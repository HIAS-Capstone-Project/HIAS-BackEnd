package com.hias.service.impl;

import com.hias.constant.CommonConstant;
import com.hias.constant.ErrorMessageCode;
import com.hias.constant.FieldNameConstant;
import com.hias.constant.RoleEnum;
import com.hias.entity.*;
import com.hias.exception.HIASException;
import com.hias.mapper.request.ClientRequestDTOMapper;
import com.hias.mapper.response.ClientResponeDTOMapper;
import com.hias.mapper.response.EmployeeResponseDTOMapper;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.*;
import com.hias.security.dto.UserDetail;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final HealthCardFormatRepository healthCardFormatRepository;
    private final ClientResponeDTOMapper clientResponeDTOMapper;
    private final EmployeeResponseDTOMapper employeeResponseDTOMapper;
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

        Page<Client> clientPage = this.buildClientPageByRole(searchValue, pageable);

        if (!clientPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Client> clients = clientPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", clients.size());

        List<ClientResponseDTO> clientResponseDTOS = clientResponeDTOMapper.toDtoList(clients);

        clientResponseDTOS.forEach(c ->
                c.setEmployeeResponseDTOS(employeeResponseDTOMapper.toDtoList
                        (employeeClientRepository.findByClientNoAndIsDeletedIsFalse(c.getClientNo())
                                .stream()
                                .map(EmployeeClient::getEmployee)
                                .collect(Collectors.toList()))));

        clientResponseDTOS.forEach(p -> p.setBusinessSectorNos(clientBusinessSectorRepository.findAllByClientNoAndIsDeletedIsFalse(p.getClientNo()).
                stream().map(ClientBusinessSector::getBusinessSectorNo).collect(Collectors.toList())));

        return new PagingResponseModel<>(new PageImpl<>(clientResponseDTOS,
                pageable,
                clientPage.getTotalElements()));
    }

    private Page<Client> buildClientPageByRole(String searchValue, Pageable pageable) {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RoleEnum roleEnum = null;
        Long primaryKey = null;
        if (userDetail != null) {
            roleEnum = RoleEnum.findByString(userDetail.getRoles().get(0));
            primaryKey = userDetail.getPrimaryKey();
        }
        Page<Client> clientPage = Page.empty();
        if (roleEnum == null || RoleEnum.ROLE_SYSTEM_ADMIN.equals(roleEnum)) {
            clientPage = clientRepository.findAllBySearchValue(searchValue, pageable);
        }
        if (Arrays.asList(RoleEnum.ROLE_BUSINESS_EMPLOYEE).contains(roleEnum)) {
            clientPage = clientRepository.findAllBySearchValueForEmployee(primaryKey, searchValue, pageable);
        }
        return clientPage;
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
        clientBusinessSectorRepository.saveAll(clientBusinessSectors);

        List<Long> employeeNos = clientRequestDTO.getEmployeeNos();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(employeeNos)) {
            List<EmployeeClient> employeeClients = new ArrayList<>();
            employeeNos.forEach(employeeNo -> employeeClients.add(EmployeeClient.builder()
                    .employee(Employee.builder().employeeNo(employeeNo).build())
                    .client(client)
                    .build()));
            employeeClientRepository.saveAll(employeeClients);
        }
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

            this.processForEmployeeClient(clientRequestDTO, updatedClient);

            clientResponseDTO = clientResponeDTOMapper.toDto(clientRepository.save(updatedClient));
            log.info("Updated Client");
            clientBusinessSectorRepository.saveAll(updatedClientBusinessSector);
            log.info("Updated relevant business sectors in Client Business Sector");
        }
        return clientResponseDTO;
    }

    private void processForEmployeeClient(ClientRequestDTO clientRequestDTO, Client client) {
        Long clientNo = clientRequestDTO.getClientNo();
        List<EmployeeClient> employeeClients = employeeClientRepository.findByClientNo(clientNo);
        Map<Long, EmployeeClient> employeeClientMap = new HashMap<>();
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(employeeClients)) {
            employeeClientMap = employeeClients.stream()
                    .collect(Collectors.toMap(EmployeeClient::getEmployeeNo, Function.identity()));
        }
        List<EmployeeClient> savedEmployeeClients = new ArrayList<>();
        List<Long> employeeNos = clientRequestDTO.getEmployeeNos();
        EmployeeClient employeeClient;
        for (Long employeeNo : employeeNos) {
            employeeClient = employeeClientMap.get(employeeNo);
            if (employeeClient == null) {
                savedEmployeeClients.add(EmployeeClient
                        .builder()
                        .client(client)
                        .employee(Employee.builder().employeeNo(employeeNo).build())
                        .build());
            } else {
                if (employeeClient.isDeleted()) {
                    employeeClient.setDeleted(Boolean.FALSE);
                    savedEmployeeClients.add(employeeClient);
                }
            }
        }

        //remove old employees
        savedEmployeeClients.addAll(employeeClients.stream().filter(e -> !employeeNos.contains(e.getEmployeeNo()) && !e.isDeleted())
                .peek(b -> b.setDeleted(Boolean.TRUE))
                .collect(Collectors.toList()));

        employeeClientRepository.saveAll(savedEmployeeClients);
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
