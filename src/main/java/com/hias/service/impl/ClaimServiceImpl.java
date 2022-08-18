package com.hias.service.impl;

import com.hias.constant.*;
import com.hias.entity.BenefitLicense;
import com.hias.entity.Claim;
import com.hias.entity.ClaimDocument;
import com.hias.entity.License;
import com.hias.exception.HIASException;
import com.hias.mapper.request.ClaimRequestDTOMapper;
import com.hias.mapper.request.ClaimSubmitRequestDTOMapper;
import com.hias.mapper.response.ClaimDocumentResponseDTOMapper;
import com.hias.mapper.response.ClaimResponseDTOMapper;
import com.hias.mapper.response.LicenseResponseDTOMapper;
import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimDocumentResponseDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.*;
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
    private final MemberRepository memberRepository;
    private final ClaimDocumentRepository claimDocumentRepository;
    private final BenefitLiscenseRepository benefitLiscenseRepository;
    private final ClaimRequestDTOMapper claimRequestDTOMapper;
    private final ClaimSubmitRequestDTOMapper claimSubmitRequestDTOMapper;
    private final ClaimResponseDTOMapper claimResponseDTOMapper;
    private final ClaimDocumentResponseDTOMapper claimDocumentResponseDTOMapper;
    private final LicenseResponseDTOMapper licenseResponseDTOMapper;
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
            claimResponseDTO = claimResponseDTOMapper.toDto(claimOptional.get());
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
        }
        return claimResponseDTO;
    }

    @Override
    public PagingResponseModel<ClaimResponseDTO> search(String searchValue, Pageable pageable) {

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        log.info("[search] Start search with value : {}, pageNumber : {}, pageSize : {}", searchValue, pageNumber,
                pageSize);

        Page<Claim> claimPage = claimRepository.findAllBySearchValue(searchValue, pageable);

        if (!claimPage.hasContent()) {
            log.info("[search] Could not found any element match with value : {}", searchValue);
            return new PagingResponseModel<>(null);
        }

        List<Claim> claims = claimPage.getContent();

        log.info("[search] Found {} elements match with value : {}.", claims.size(), searchValue);

        List<ClaimResponseDTO> claimResponseDTOS = claimResponseDTOMapper.toDtoList(claims);

        return new PagingResponseModel<>(new PageImpl<>(claimResponseDTOS,
                pageable,
                claimPage.getTotalElements()));
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
    public ClaimResponseDTO update(ClaimRequestDTO claimRequestDTO) {
        Claim claim = claimRequestDTOMapper.toEntity(claimRequestDTO);
        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
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

        validateVisitDate(claimSubmitRequestDTO);

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

        List<ClaimDocument> claimDocuments = claimDocumentRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        Map<Long, ClaimDocument> claimDocumentMap = buildClaimDocumentMap(claimDocuments);

        processClaimDocuments(claimSubmitRequestDTO, files, claimDocuments, claimDocumentMap);

        Optional<Long> employeeNo = employeeRepository.findBusinessAppraiserHasClaimAtLeast();
        if (employeeNo.isPresent()) {
            claimSaved.setBusinessAppraisalBy(employeeNo.get());
            claimSaved = claimRepository.save(claimSaved);
        }
        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimSaved);
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO saveDraft(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException {

        validateDraftClaim(claimSubmitRequestDTO);

        validateVisitDate(claimSubmitRequestDTO);

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

        List<ClaimDocument> claimDocuments = claimDocumentRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        Map<Long, ClaimDocument> claimDocumentMap = buildClaimDocumentMap(claimDocuments);

        processClaimDocuments(claimSubmitRequestDTO, files, claimDocuments, claimDocumentMap);

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
                                       List<ClaimDocument> claimDocuments,
                                       Map<Long, ClaimDocument> claimDocumentMap) throws IOException {
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

            Optional<Long> employeeNo = employeeRepository.findMedicalAppraiserHasClaimAtLeast();
            if (employeeNo.isPresent()) {
                claim.setMedicalAppraisalBy(employeeNo.get());
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

            Optional<Long> employeeNo = employeeRepository.findApproverHasClaimAtLeast();
            if (employeeNo.isPresent()) {
                claim.setApprovedBy(employeeNo.get());
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

}
