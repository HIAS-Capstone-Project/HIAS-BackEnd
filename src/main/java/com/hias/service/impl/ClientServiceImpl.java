package com.hias.service.impl;

import com.hias.constant.CommonConstant;
import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.entity.*;
import com.hias.exception.HIASException;
import com.hias.mapper.request.ClientRequestDTOMapper;
import com.hias.mapper.response.ClientResponeDTOMapper;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponeDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.*;
import com.hias.service.ClientService;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.ClientValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final HealthCardFormatRepository healthCardFormatRepository;
    private final ClientResponeDTOMapper clientResponeDTOMapper;
    private final ClientRequestDTOMapper clientRequestDTOMapper;
    private final ClientValidator clientValidator;
    private final EmployeeClientRepository employeeClientRepository;
    private final PolicyRepository policyRepository;
    private final PolicyCoverageRepository policyCoverageRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ClientResponeDTO> getAll() {
        log.info("[getAll] Start get all clients.");
        List<ClientResponeDTO> clientResponeDTOList = new ArrayList<>();

        List<Client> clients = clientRepository.findByIsDeletedIsFalse();

        if (!CollectionUtils.isEmpty(clients)) {
            log.info("[getAll] Size of clients : {}.", clients.size());
            clientResponeDTOList = clientResponeDTOMapper.toDtoList(clients);
        }
        return clientResponeDTOList;
    }

    @Override
    public ClientResponeDTO getDetail(Long clientNo) {
        log.info("[getDetail] start get detail client");
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        Optional<Client> optionalClient = clientRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
        if (optionalClient.isPresent()) {
            clientResponeDTO = clientResponeDTOMapper.toDto(optionalClient.get());
        }
        return clientResponeDTO;
    }

    @Override
    public PagingResponseModel<ClientResponeDTO> search(String searchValue, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Client> clientPage = clientRepository.findAllBySearchValue(searchValue, pageable);

        if (!clientPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Client> clients = clientPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", clients.size());

        List<ClientResponeDTO> clientResponeDTOS = clientResponeDTOMapper.toDtoList(clients);

        return new PagingResponseModel<>(new PageImpl<>(clientResponeDTOS,
                pageable,
                clientPage.getTotalElements()));
    }

    @Override
    @Transactional
    public ClientResponeDTO create(ClientRequestDTO clientRequestDTO) throws HIASException {
        String corporateID = clientRequestDTO.getCorporateID();
        if (clientValidator.isCorporateIDExistance(corporateID)) {
            throw HIASException.buildHIASException(FieldNameConstant.CORPORATE_ID,
                    MessageUtils.get().getMessage(ErrorMessageCode.CORPORATE_ID_EXISTENCE),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        log.info("[createClient] start get detail client");
        Client client = clientRepository.save(clientRequestDTOMapper.toEntity(clientRequestDTO));
        if (client != null) {
            healthCardFormatRepository.save(HealthCardFormat.builder()
                    .client(client)
                    .clientNo(client.getClientNo())
                    .prefix(StringUtils.upperCase(corporateID) + CommonConstant.DASH)
                    .build());
        }
        log.info("create client successfully");
        return clientResponeDTOMapper.toDto(client);
    }

    @Override
    @Transactional
    public ClientResponeDTO update(ClientRequestDTO clientRequestDTO) {
        log.info("[updateClient] update client");
        Optional<Client> optionalClient = clientRepository.findByClientNoAndIsDeletedIsFalse(clientRequestDTO.getClientNo());
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        if (optionalClient.isPresent()) {
            Client updatedClient = clientRequestDTOMapper.toEntity(clientRequestDTO);
            clientResponeDTO = clientResponeDTOMapper.toDto(clientRepository.save(updatedClient));
        }
        return clientResponeDTO;
    }

    @Override
    @Transactional
    public ClientResponeDTO delete(Long clientNo) {
        log.info("[deleteClient] start delete client {}", clientNo);
        Optional<Client> optionalClient = clientRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            if (!client.isDeleted()) {
                List<Policy> policyList = policyRepository.findAllByClientNoAndIsDeletedIsFalse(clientNo);
                if (!policyList.isEmpty()) {
                    List<Long> policyNos = policyList.stream().map(policy -> policy.getPolicyNo()).collect(Collectors.toList());
                    for (Policy policy : policyList) {
                        policy.setDeleted(true);
                    }
                    List<PolicyCoverage> policyCoverageList = policyCoverageRepository.findAllByPolicyNos(policyNos);
                    if (!CollectionUtils.isEmpty(policyCoverageList)) {
                        for (PolicyCoverage policyCoverage : policyCoverageList) {
                            policyCoverage.setDeleted(true);
                        }
                        policyCoverageRepository.saveAll(policyCoverageList);
                    }
                    policyRepository.saveAll(policyList);
                }
                List<Member> memberList = memberRepository.findMemberByClientNoAndIsDeletedIsFalse(clientNo);
                if (!memberList.isEmpty()) {
                    for (Member member : memberList) {
                        member.setDeleted(true);
                    }
                    memberRepository.saveAll(memberList);
                }
                List<EmployeeClient> employeeClients = employeeClientRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
                if (!CollectionUtils.isEmpty(employeeClients)) {
                    for (EmployeeClient employeeClient : employeeClients) {
                        employeeClient.setDeleted(true);
                    }
                    employeeClientRepository.saveAll(employeeClients);
                }
                List<HealthCardFormat> healthCardFormatList = healthCardFormatRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
                if (!CollectionUtils.isEmpty(healthCardFormatList)) {
                    for (HealthCardFormat healthCardFormat : healthCardFormatList) {
                        healthCardFormat.setDeleted(true);
                    }
                    healthCardFormatRepository.saveAll(healthCardFormatList);
                }
                client.setDeleted(true);
                clientResponeDTO = clientResponeDTOMapper.toDto(clientRepository.save(client));
            }
        }
        return clientResponeDTO;
    }

    @Override
    public List<ClientResponeDTO> findByEmployeeNo(Long employeeNo) {
        return employeeClientRepository.findByEmployeeNoAndIsDeletedIsFalse(employeeNo).
                stream().map(o -> getDetail(o.getClientNo())).collect(Collectors.toList());
    }
}
