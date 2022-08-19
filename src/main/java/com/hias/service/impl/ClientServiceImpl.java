package com.hias.service.impl;

import com.hias.constant.CommonConstant;
import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.entity.*;
import com.hias.exception.HIASException;
import com.hias.mapper.request.ClientRequestDTOMapper;
import com.hias.mapper.response.ClientResponeDTOMapper;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponseDTO;
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
import java.util.Objects;
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
    private final ClientBusinessSectorRepository clientBusinessSectorRepository;

    @Override
    public List<ClientResponseDTO> getAll() {
        log.info("[getAll] Start get all clients.");
        List<ClientResponseDTO> clientResponseDTOList = new ArrayList<>();

        List<Client> clients = clientRepository.findByIsDeletedIsFalse();

        if (!CollectionUtils.isEmpty(clients)) {
            log.info("[getAll] Size of clients : {}.", clients.size());
            clientResponseDTOList = clientResponeDTOMapper.toDtoList(clients);
        }
        return clientResponseDTOList;
    }

    @Override
    public ClientResponseDTO getDetail(Long clientNo) {
        log.info("[getDetail] start get detail client");
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        Optional<Client> optionalClient = clientRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
        if (optionalClient.isPresent()) {
            clientResponseDTO = clientResponeDTOMapper.toDto(optionalClient.get());
        }
        return clientResponseDTO;
    }

    @Override
    public PagingResponseModel<ClientResponseDTO> search(String searchValue, Pageable pageable) {
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

        List<ClientResponseDTO> clientResponseDTOS = clientResponeDTOMapper.toDtoList(clients);

        clientResponseDTOS.forEach(p -> p.setBusinessSectorNos(clientBusinessSectorRepository.findAllByClientNoAndIsDeletedIsFalse(p.getClientNo()).
                stream().map(ClientBusinessSector::getBusinessSectorNo).collect(Collectors.toList())));

        return new PagingResponseModel<>(new PageImpl<>(clientResponseDTOS,
                pageable,
                clientPage.getTotalElements()));
    }

    @Override
    public PagingResponseModel<ClientResponseDTO> searchForEmployee(Long employeeNo, String searchValue, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Client> clientPage = clientRepository.findAllBySearchValueForEmployee(employeeNo, searchValue, pageable);

        if (!clientPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Client> clients = clientPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", clients.size());

        List<ClientResponseDTO> clientResponseDTOS = clientResponeDTOMapper.toDtoList(clients);

        clientResponseDTOS.forEach(p -> p.setBusinessSectorNos(clientBusinessSectorRepository.findAllByClientNoAndIsDeletedIsFalse(p.getClientNo()).
                stream().map(ClientBusinessSector::getBusinessSectorNo).collect(Collectors.toList())));

        return new PagingResponseModel<>(new PageImpl<>(clientResponseDTOS,
                pageable,
                clientPage.getTotalElements()));
    }

    @Override
    @Transactional
    public ClientResponseDTO create(ClientRequestDTO clientRequestDTO) throws HIASException {
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
        log.info("Created Client with ID: {}", client.getClientNo());
        List<ClientBusinessSector> clientBusinessSectors = new ArrayList<>();
        clientRequestDTO.getBusinessSectorNos().forEach(o -> clientBusinessSectors.add(ClientBusinessSector.builder().clientNo(client.getClientNo()).businessSectorNo(o).
                client(Client.builder().clientNo(client.getClientNo()).build()).
                businessSector(BusinessSector.builder().businessSectorNo(o).build()).build()));
        clientBusinessSectorRepository.saveAllAndFlush(clientBusinessSectors);
        log.info("create client successfully");
        return clientResponeDTOMapper.toDto(client);
    }

    @Override
    @Transactional
    public ClientResponseDTO update(ClientRequestDTO clientRequestDTO) {
        log.info("[updateClient] update client");
        Optional<Client> optionalClient = clientRepository.findByClientNoAndIsDeletedIsFalse(clientRequestDTO.getClientNo());
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        if (optionalClient.isPresent()) {
            Client updatedClient = clientRequestDTOMapper.toEntity(clientRequestDTO);
            List<ClientBusinessSector> clientBusinessSectors = clientBusinessSectorRepository.findAllByClientNo(updatedClient.getClientNo());
            List<ClientBusinessSector> updatedClientBusinessSector = new ArrayList<>();
            clientRequestDTO.getBusinessSectorNos().forEach(o -> {
                if (clientBusinessSectors.stream().anyMatch(p -> Objects.equals(p.getBusinessSectorNo(), o) && p.isDeleted())) {
                    updatedClientBusinessSector.add(clientBusinessSectors.stream().filter(p -> (Objects.equals(p.getBusinessSectorNo(), o))).
                            peek(p -> p.setDeleted(false)).findFirst().get());
                } else {
                    if (clientBusinessSectors.stream().noneMatch(p -> Objects.equals(p.getBusinessSectorNo(), o))) {
                        updatedClientBusinessSector.add(ClientBusinessSector.builder().clientNo(updatedClient.getClientNo()).businessSectorNo(o).
                                client(Client.builder().clientNo(updatedClient.getClientNo()).build()).
                                businessSector(BusinessSector.builder().businessSectorNo(o).build()).build());
                    }
                }
            });
            clientBusinessSectors.forEach(o -> {
                if (clientRequestDTO.getBusinessSectorNos().stream().noneMatch(b -> Objects.equals(b, o.getBusinessSectorNo()))) {
                    o.setDeleted(true);
                    updatedClientBusinessSector.add(o);
                }
            });
            clientResponseDTO = clientResponeDTOMapper.toDto(clientRepository.save(updatedClient));
            log.info("Updated Client");
            clientBusinessSectorRepository.saveAllAndFlush(updatedClientBusinessSector);
            log.info("Updated relevant business sectors in Client Business Sector");
        }
        return clientResponseDTO;
    }

    @Override
    @Transactional
    public ClientResponseDTO delete(Long clientNo) {
        log.info("[deleteClient] start delete client {}", clientNo);
        Optional<Client> optionalClient = clientRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
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
                clientResponseDTO = clientResponeDTOMapper.toDto(clientRepository.save(client));
            }
        }
        return clientResponseDTO;
    }

    @Override
    public List<ClientResponseDTO> findByEmployeeNo(Long employeeNo) {
        return employeeClientRepository.findByEmployeeNoAndIsDeletedIsFalse(employeeNo).
                stream().map(o -> getDetail(o.getClientNo())).collect(Collectors.toList());
    }
}
