package com.hias.service.impl;

import com.hias.constant.*;
import com.hias.entity.*;
import com.hias.exception.HIASException;
import com.hias.mapper.request.ClaimRequestDTOMapper;
import com.hias.mapper.request.ClaimSubmitRequestDTOMapper;
import com.hias.mapper.response.*;
import com.hias.model.request.*;
import com.hias.model.response.ClaimDocumentResponseDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.*;
import com.hias.security.dto.UserDetail;
import com.hias.service.ClaimService;
import com.hias.utils.DateUtils;
import com.hias.utils.FireBaseUtils;
import com.hias.utils.MessageUtils;
import com.hias.utils.validator.ClaimValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final EmployeeRepository employeeRepository;
    private final ClaimDocumentRepository claimDocumentRepository;
    private final BenefitLiscenseRepository benefitLiscenseRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final ClaimRequestDTOMapper claimRequestDTOMapper;
    private final ClaimSubmitRequestDTOMapper claimSubmitRequestDTOMapper;
    private final ClaimResponseDTOMapper claimResponseDTOMapper;
    private final ClaimDocumentResponseDTOMapper claimDocumentResponseDTOMapper;
    private final LicenseResponseDTOMapper licenseResponseDTOMapper;
    private final MemberResponseDTOMapper memberResponseDTOMapper;
    private final ClientResponeDTOMapper clientResponeDTOMapper;
    private final PolicyResponseDTOMapper policyResponseDTOMapper;
    private final BenefitResponseDTOMapper benefitResponseDTOMapper;
    private final ServiceProviderResponseDTOMapper serviceProviderResponseDTOMapper;
    private final EmployeeResponseDTOMapper employeeResponseDTOMapper;
    private final FireBaseUtils fireBaseUtils;
    private final MessageUtils messageUtils;
    private final ClaimValidator claimValidator;

    private Map<StatusCode, StatusCode> nextStatusMap;

    @PostConstruct
    private void initNextStatusMap() {
        nextStatusMap = new HashMap<>();
        nextStatusMap.put(StatusCode.SUBMITTED, StatusCode.BUSINESS_VERIFYING);
        nextStatusMap.put(StatusCode.BUSINESS_VERIFIED, StatusCode.MEDICAL_VERIFYING);
        nextStatusMap.put(StatusCode.MEDICAL_VERIFIED, StatusCode.WAITING_FOR_APPROVAL);
        nextStatusMap.put(StatusCode.APPROVED, StatusCode.PAYMENT_PROCESSING);
    }

    @Override
    public List<ClaimResponseDTO> findAll() {
        List<Claim> claims = claimRepository.findAllByIsDeletedIsFalse();
        return claimResponseDTOMapper.toDtoList(claims);
    }

    @Override
    public ClaimResponseDTO viewDetail(Long claimNo) {
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claimResponseDTO = claimResponseDTOMapper.toDto(claim);
            List<ClaimDocument> claimDocuments = claimDocumentRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
            if (CollectionUtils.isNotEmpty(claimDocuments)) {
                List<ClaimDocumentResponseDTO> claimDocumentResponseDTOS = new ArrayList<>();
                License license;
                for (ClaimDocument claimDocument : claimDocuments) {
                    ClaimDocumentResponseDTO claimDocumentResponseDTO = claimDocumentResponseDTOMapper.toDto(claimDocument);

                    license = claimDocument.getLicense();
                    claimDocumentResponseDTO.setLicenseResponseDTO(licenseResponseDTOMapper.toDto(license));
                    claimDocumentResponseDTOS.add(claimDocumentResponseDTO);
                }
                claimResponseDTO.setClaimDocumentResponseDTOS(claimDocumentResponseDTOS);
            }
            Member member = claim.getMember();
            claimResponseDTO.setMemberResponseDTO(memberResponseDTOMapper.toDto(member));
            claimResponseDTO.setClientResponseDTO(clientResponeDTOMapper.toDto(member.getClient()));
            claimResponseDTO.setPolicyResponseDTO(policyResponseDTOMapper.toDto(member.getPolicy()));
            claimResponseDTO.setBenefitResponseDTO(benefitResponseDTOMapper.toDto(claim.getBenefit()));

            Long serviceProviderNo = claim.getServiceProviderNo();
            if (serviceProviderNo != null) {
                claimResponseDTO.setServiceProviderResponseDTO(
                        serviceProviderResponseDTOMapper.toDto(
                                serviceProviderRepository.findByServiceProviderNoAndIsDeletedIsFalse(serviceProviderNo).get()));
            }

            Long businessAppraisalBy = claim.getBusinessAppraisalBy();
            if (businessAppraisalBy != null) {
                claimResponseDTO.setBusinessAppraisal(employeeResponseDTOMapper
                        .toDto(employeeRepository.findByEmployeeNoAndIsDeletedIsFalse(businessAppraisalBy).get()));
            }

            Long medicalAppraisalBy = claim.getMedicalAppraisalBy();
            if (medicalAppraisalBy != null) {
                claimResponseDTO.setMedicalAppraisal(employeeResponseDTOMapper
                        .toDto(employeeRepository.findByEmployeeNoAndIsDeletedIsFalse(medicalAppraisalBy).get()));
            }

            Long approvedBy = claim.getApprovedBy();
            if (approvedBy != null) {
                claimResponseDTO.setApprover(employeeResponseDTOMapper
                        .toDto(employeeRepository.findByEmployeeNoAndIsDeletedIsFalse(approvedBy).get()));
            }

            Long paidBy = claim.getPaidBy();
            if (paidBy != null) {
                claimResponseDTO.setAccountant(employeeResponseDTOMapper
                        .toDto(employeeRepository.findByEmployeeNoAndIsDeletedIsFalse(paidBy).get()));
            }

        }
        return claimResponseDTO;
    }

    @Override
    public PagingResponseModel<ClaimResponseDTO> search(String searchValue, Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Claim> claimPage = this.buildClaimPageByRole(searchValue, pageable);

        if (!claimPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Claim> claims = claimPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", claims.size(), searchValue);

        List<ClaimResponseDTO> claimResponseDTOS = new ArrayList<>();
        Member member;
        for (Claim claim : claims) {
            member = claim.getMember();
            ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claim);
            claimResponseDTO.setBenefitResponseDTO(benefitResponseDTOMapper.toDto(claim.getBenefit()));
            claimResponseDTO.setMemberResponseDTO(memberResponseDTOMapper.toDto(member));
            claimResponseDTO.setClientResponseDTO(clientResponeDTOMapper.toDto(member.getClient()));
            claimResponseDTOS.add(claimResponseDTO);
        }

        return new PagingResponseModel<>(new PageImpl<>(claimResponseDTOS,
                pageable,
                claimPage.getTotalElements()));
    }

    private Page<Claim> buildClaimPageByRole(String searchValue, Pageable pageable) {
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RoleEnum roleEnum = null;
        Long primaryKey = null;
        if (userDetail != null) {
            roleEnum = RoleEnum.findByString(userDetail.getRoles().get(0));
            primaryKey = userDetail.getPrimaryKey();
        }
        Page<Claim> claimPage = null;
        if (roleEnum == null || RoleEnum.ROLE_SYSTEM_ADMIN.equals(roleEnum)) {
            claimPage = claimRepository.findAllBySearchValue(searchValue, pageable);
        }
        if (RoleEnum.ROLE_MEMBER.equals(roleEnum)) {
            claimPage = claimRepository.findAllBySearchValueForMember(primaryKey, searchValue, pageable);
        }
        if (Arrays.asList(RoleEnum.ROLE_BUSINESS_APPRAISER,
                        RoleEnum.ROLE_MEDICAL_APPRAISER,
                        RoleEnum.ROLE_ACCOUNTANT,
                        RoleEnum.ROLE_HEALTH_MODERATOR)
                .contains(roleEnum)) {
            claimPage = claimRepository.findAllBySearchValueForEmployee(primaryKey, searchValue, pageable);
        }
        if (RoleEnum.ROLE_SERVICE_PROVIDER.equals(roleEnum)) {
            claimPage = claimRepository.findAllBySearchValueForServiceProvider(primaryKey, searchValue, pageable);
        }
        return claimPage;
    }

    @Override
    @Transactional
    public ClaimResponseDTO create(ClaimRequestDTO claimRequestDTO) {
        Claim claim = claimRequestDTOMapper.toEntity(claimRequestDTO);
        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO update(ClaimUpdateRequestDTO claimUpdateRequestDTO) {
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        Long claimNo = claimUpdateRequestDTO.getClaimNo();
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        if (claimOptional.isPresent()) {
            Claim claimUpdated = claimOptional.get();
            claimUpdated.setBusinessAppraisalBy(claimUpdateRequestDTO.getBusinessAppraisalBy());
            claimUpdated.setMedicalAppraisalBy(claimUpdateRequestDTO.getMedicalAppraisalBy());
            claimUpdated.setApprovedBy(claimUpdateRequestDTO.getApprovedBy());
            claimUpdated.setPaidBy(claimUpdateRequestDTO.getPaidBy());
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claimUpdated));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO deleteByClaimNo(Long claimNo) {
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        if (!claimOptional.isPresent()) {
            log.info("[deleteByBenefitNo] Cannot found claim with claimNo : {} in the system.", claimNo);
        } else {
            Claim claim = claimOptional.get();
            claim.setDeleted(Boolean.TRUE);
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
            log.info("[deleteByBenefitNo] Delete claim with claimNo : {} in the system.", claimNo);
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO submit(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException {

        Claim claim = claimSubmitRequestDTOMapper.toEntity(claimSubmitRequestDTO);
        claim.setStatusCode(StatusCode.SUBMITTED);
        claim.setSubmittedDate(LocalDateTime.now());
        claim.setRecordSource((claimSubmitRequestDTO.getServiceProviderNo() == null) ? RecordSource.M : RecordSource.SVP);

        Claim claimSaved = claimRepository.save(claim);
        Long claimNo = claimSubmitRequestDTO.getClaimNo();
        Long claimNoSaved = claimSaved.getClaimNo();

        Long benefitNo = claimSubmitRequestDTO.getBenefitNo();
        List<BenefitLicense> benefitLicenses = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        List<License> licenses = benefitLicenses.stream().map(BenefitLicense::getLicense).collect(Collectors.toList());

        initClaimDocumentsIfNeeded(claimSaved, claimNo, claimNoSaved, licenses);

        List<ClaimDocument> claimDocuments = claimDocumentRepository.findByClaimNoAndIsDeletedIsFalse(claimNoSaved);
        Map<Long, ClaimDocument> claimDocumentMap = buildClaimDocumentMap(claimDocuments);

        processClaimDocuments(claimSubmitRequestDTO, files, claimDocumentMap);


        List<Long> employeeNos = employeeRepository.findBusinessAppraiserHasClaimAtLeast();
        if (CollectionUtils.isNotEmpty(employeeNos)) {
            claimSaved.setBusinessAppraisalBy(employeeNos.get(0));
            claimSaved = claimRepository.save(claimSaved);
        }
        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimSaved);
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO saveDraft(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException {

        Claim claim = claimSubmitRequestDTOMapper.toEntity(claimSubmitRequestDTO);
        claim.setStatusCode(StatusCode.DRAFT);
        claim.setRecordSource((claimSubmitRequestDTO.getServiceProviderNo() == null) ? RecordSource.M : RecordSource.SVP);

        Claim claimSaved = claimRepository.save(claim);
        Long claimNo = claimSubmitRequestDTO.getClaimNo();
        Long claimNoSaved = claimSaved.getClaimNo();

        Long benefitNo = claimSubmitRequestDTO.getBenefitNo();
        List<BenefitLicense> benefitLicenses = benefitLiscenseRepository.findByBenefitNoAndIsDeletedIsFalse(benefitNo);
        List<License> licenses = benefitLicenses.stream().map(BenefitLicense::getLicense).collect(Collectors.toList());

        initClaimDocumentsIfNeeded(claimSaved, claimNo, claimNoSaved, licenses);

        List<ClaimDocument> claimDocuments = claimDocumentRepository.findByClaimNoAndIsDeletedIsFalse(claimNoSaved);
        Map<Long, ClaimDocument> claimDocumentMap = buildClaimDocumentMap(claimDocuments);

        processClaimDocuments(claimSubmitRequestDTO, files, claimDocumentMap);

        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimSaved);

        return claimResponseDTO;
    }

    private static Map<Long, ClaimDocument> buildClaimDocumentMap(List<ClaimDocument> claimDocuments) {
        Map<Long, ClaimDocument> claimDocumentMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(claimDocuments)) {
            claimDocumentMap = claimDocuments
                    .stream()
                    .collect(Collectors.toMap(ClaimDocument::getLicenseNo, Function.identity()));
            claimDocuments.clear();
        }
        return claimDocumentMap;
    }

    private void processClaimDocuments(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files,
                                       Map<Long, ClaimDocument> claimDocumentMap) throws IOException {
        List<ClaimDocument> claimDocuments = new ArrayList<>();
        if (files == null) {
            files = new ArrayList<>();
        }
        List<Long> licenseNos = claimSubmitRequestDTO.getLicenseNos();
        int size = Math.min(files.size(), licenseNos.size());

        Long licenseNo;
        MultipartFile file;
        String originalFileName, fileName, extension;

        for (int index = 0; index < size; index++) {
            licenseNo = licenseNos.get(index);
            file = files.get(index);
            if (file != null) {
                originalFileName = file.getOriginalFilename().trim();
                fileName = originalFileName.replaceAll(CommonConstant.REGEX_FILE_EXTENSION, StringUtils.EMPTY);
                extension = org.springframework.util.StringUtils.getFilenameExtension(originalFileName);
                fileName = fileName + DateUtils.currentDateTimeAsString(DateConstant.DD_MM_YYYY_HH_MM_SS_SSS) + CommonConstant.DOT + extension;
                fireBaseUtils.uploadFile(file, fileName);
                ClaimDocument claimDocument = claimDocumentMap.get(licenseNo);
                claimDocument.setPathFile(fileName);
                claimDocument.setFileName(fileName);
                claimDocument.setOriginalFileName(originalFileName);
                claimDocument.setFileUrl(String.format(FireBaseConstant.FILE_URL, fileName));
                claimDocuments.add(claimDocument);
            }
        }
        claimDocumentRepository.saveAll(claimDocuments);
    }

    private void initClaimDocumentsIfNeeded(Claim claimSaved, Long claimNo, Long claimNoSaved, List<License> licenses) {
        //init claim documents when create
        if (claimNo == null) {
            List<ClaimDocument> claimDocuments = new ArrayList<>();
            for (License license : licenses) {
                claimDocuments.add(ClaimDocument.builder()
                        .claim(claimSaved)
                        .claimNo(claimNoSaved)
                        .license(license)
                        .licenseNo(license.getLicenseNo())
                        .build());
            }
            claimDocumentRepository.saveAll(claimDocuments);
        }
    }

    private void validateDraftClaim(ClaimSubmitRequestDTO claimSubmitRequestDTO) throws HIASException {
        if (claimSubmitRequestDTO.getClaimNo() == null) {
            return;
        }
        if (!claimValidator.isDraftClaim(claimSubmitRequestDTO.getClaimNo())) {
            throw HIASException.buildHIASException(
                    messageUtils.getMessage(ErrorMessageCode.NOT_DRAFT_CLAIM),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private void validateVisitDate(ClaimSubmitRequestDTO claimSubmitRequestDTO) throws HIASException {
        Long memberNo = claimSubmitRequestDTO.getMemberNo();
        LocalDate visitDate = claimSubmitRequestDTO.getVisitDate().toLocalDate();
        if (!claimValidator.isValidBenefitPeriod(memberNo, visitDate)) {
            throw HIASException.buildHIASException(FieldNameConstant.VISIT_DATE,
                    messageUtils.getMessage(ErrorMessageCode.INVALID_MEMBER_VISIT_DATE),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    @Transactional
    public ClaimResponseDTO cancelClaim(Long claimNo) throws HIASException {
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            if (!StatusCode.DRAFT.equals(claim.getStatusCode())) {
                throw HIASException.buildHIASException(
                        messageUtils.getMessage(ErrorMessageCode.NOT_DRAFT_CLAIM),
                        HttpStatus.NOT_ACCEPTABLE);
            }
            claim.setStatusCode(StatusCode.CANCELED);
            claim.setCanceledDate(LocalDateTime.now());
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO businessVerified(Long claimNo) {
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claim.setStatusCode(StatusCode.BUSINESS_VERIFIED);
            claim.setBusinessAppraisalDate(LocalDateTime.now());

            if (claim.getMedicalAppraisalBy() == null) {
                List<Long> employeeNos = employeeRepository.findMedicalAppraiserHasClaimAtLeast();
                if (CollectionUtils.isNotEmpty(employeeNos)) {
                    claim.setMedicalAppraisalBy(employeeNos.get(0));
                }
            }
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO medicalVerified(Long claimNo) {
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claim.setStatusCode(StatusCode.MEDICAL_VERIFIED);
            claim.setMedicalAppraisalDate(LocalDateTime.now());

            if (claim.getApprovedBy() == null) {
                List<Long> employeeNos = employeeRepository.findApproverHasClaimAtLeast();
                if (CollectionUtils.isNotEmpty(employeeNos)) {
                    claim.setApprovedBy(employeeNos.get(0));
                }
            }
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO startProgress(Long claimNo) {
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claim.setStatusCode(nextStatusMap.get(claim.getStatusCode()));
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO approve(Long claimNo) {
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claim.setStatusCode(StatusCode.APPROVED);
            claim.setApprovedDate(LocalDateTime.now());

            if (claim.getPaidBy() == null) {
                List<Long> employeeNos = employeeRepository.findAccountantHasClaimAtLeast();
                if (CollectionUtils.isNotEmpty(employeeNos)) {
                    claim.setPaidBy(employeeNos.get(0));
                }
            }
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO settleClaim(ClaimPaymentRequestDTO claimPaymentRequestDTO) {
        Long claimNo = claimPaymentRequestDTO.getClaimNo();
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claim.setStatusCode(StatusCode.SETTLED);
            claim.setPaymentDate(LocalDateTime.now());
            claim.setRemark(claimPaymentRequestDTO.getRemark());
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO rejectClaim(ClaimRejectRequestDTO claimRejectRequestDTO) {
        Long claimNo = claimRejectRequestDTO.getClaimNo();
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claim.setStatusCode(StatusCode.REJECTED);
            claim.setStatusReasonCode(claimRejectRequestDTO.getStatusReasonCode());
            claim.setRejectedDate(LocalDateTime.now());
            claim.setRemark(claimRejectRequestDTO.getRejectReason());
            claim.setRejectReason(claimRejectRequestDTO.getRejectReason());
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO returnClaim(ClaimReturnRequestDTO claimReturnRequestDTO) {
        Long claimNo = claimReturnRequestDTO.getClaimNo();
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        if (claimOptional.isPresent()) {
            Claim claim = claimOptional.get();
            claim.setStatusCode(StatusCode.RETURN);
            claim.setStatusReasonCode(claimReturnRequestDTO.getReturnReasonCode());
            claim.setReturnDate(LocalDateTime.now());
            claim.setRemark(claimReturnRequestDTO.getReturnReason());
            claim.setReturnReason(claimReturnRequestDTO.getReturnReason());
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        }
        return claimResponseDTO;
    }

}
