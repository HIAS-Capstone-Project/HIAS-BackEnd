package com.hias.service.impl;

import com.hias.constant.*;
import com.hias.entity.Claim;
import com.hias.entity.ClaimDocument;
import com.hias.entity.ClaimRequestHistory;
import com.hias.entity.License;
import com.hias.exception.HIASException;
import com.hias.mapper.request.ClaimRequestDTOMapper;
import com.hias.mapper.request.ClaimSubmitRequestDTOMapper;
import com.hias.mapper.response.ClaimResponseDTOMapper;
import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.repository.ClaimDocumentRepository;
import com.hias.repository.ClaimRepository;
import com.hias.repository.EmployeeRepository;
import com.hias.repository.MemberRepository;
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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final ClaimRequestDTOMapper claimRequestDTOMapper;
    private final ClaimSubmitRequestDTOMapper claimSubmitRequestDTOMapper;
    private final ClaimResponseDTOMapper claimResponseDTOMapper;
    private final FireBaseUtils fireBaseUtils;
    private final MessageUtils messageUtils;
    private final ClaimValidator claimValidator;

    @Override
    public List<ClaimResponseDTO> findAll() {
        List<Claim> claims = claimRepository.findAllByIsDeletedIsFalse();
        return claimResponseDTOMapper.toDtoList(claims);
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
    public ClaimResponseDTO submitForMember(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException {

        validateVisitDate(claimSubmitRequestDTO);

        Claim claim = claimSubmitRequestDTOMapper.toEntity(claimSubmitRequestDTO);
        claim.setStatusCode(StatusCode.SUBMITTED);
        claim.setRecordSource(RecordSource.M);

        Claim claimCreated = claimRepository.save(claim);

        processClaimDocuments(claimSubmitRequestDTO, files, claimCreated);

        Optional<Long> employeeNo = employeeRepository.findBusinessAppraiserHasClaimAtLeast();
        if (employeeNo.isPresent()) {
            claimCreated.setBusinessAppraisalBy(employeeNo.get());
            claimCreated.setStatusCode(StatusCode.BUSINESS_VERIFYING);
            claimCreated.setSubmittedDate(LocalDateTime.now());
            claimCreated = claimRepository.save(claimCreated);
        }

        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimCreated);
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO saveDraftForMember(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException {

        validateDraftClaim(claimSubmitRequestDTO);

        validateVisitDate(claimSubmitRequestDTO);

        Claim claim = claimSubmitRequestDTOMapper.toEntity(claimSubmitRequestDTO);
        claim.setStatusCode(StatusCode.DRAFT);
        claim.setRecordSource(RecordSource.M);

        Claim claimSaved = claimRepository.save(claim);

        processClaimDocuments(claimSubmitRequestDTO, files, claimSaved);

        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimSaved);

        return claimResponseDTO;
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

    private void processClaimDocuments(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files, Claim claimCreated) throws IOException {
        Long claimNo = claimSubmitRequestDTO.getClaimNo();
        List<ClaimDocument> claimDocuments = claimDocumentRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);

        Map<Long, ClaimDocument> claimDocumentMap = new HashMap<>();

        if (CollectionUtils.isNotEmpty(claimDocuments)) {
            claimDocumentMap = claimDocuments
                    .stream()
                    .collect(Collectors.toMap(ClaimDocument::getLicenseNo, Function.identity()));
        }

        String fileName, extension, originalFileName;
        MultipartFile file;
        List<Long> licenseNos = claimSubmitRequestDTO.getLicenseNos();
        Long licenseNo, claimDocumentNo = null;
        int size = Math.min(files.size(), licenseNos.size());
        for (int index = 0; index < size; index++) {
            licenseNo = licenseNos.get(index);
            ClaimDocument claimDocument = claimDocumentMap.get(licenseNo);
            if (claimDocument != null) {
                claimDocumentNo = claimDocument.getClaimDocumentNo();
            }

            file = files.get(index);
            originalFileName = file.getOriginalFilename().trim();
            fileName = originalFileName.replaceAll(CommonConstant.REGEX_FILE_EXTENSION, StringUtils.EMPTY);
            extension = org.springframework.util.StringUtils.getFilenameExtension(originalFileName);
            fileName = fileName + DateUtils.currentDateTimeAsString(DateConstant.DD_MM_YYYY_HH_MM_SS_SSS) + CommonConstant.DOT + extension;
            fireBaseUtils.uploadFile(file, fileName);

            saveClaimDocumentForMember(claimCreated, originalFileName, fileName, claimDocumentNo, licenseNos, index);
        }
    }

    private void saveClaimDocumentForMember(Claim claimCreated, String originalFileName, String fileName, Long claimDocumentNo, List<Long> licenseNos, int index) {
        ClaimDocument claimDocument = new ClaimDocument();
        if (claimDocumentNo != null) {
            claimDocument.setClaimDocumentNo(claimDocumentNo);
        }
        claimDocument.setClaimNo(claimCreated.getClaimNo());
        claimDocument.setClaim(claimCreated);
        claimDocument.setClaimNo(claimCreated.getClaimNo());
        claimDocument.setLicense(License.builder().licenseNo(licenseNos.get(index)).build());
        claimDocument.setLicenseNo(licenseNos.get(index));
        claimDocument.setPathFile(fileName);
        claimDocument.setFileName(fileName);
        claimDocument.setOriginalFileName(originalFileName);
        claimDocument.setFileUrl(String.format(FireBaseConstant.FILE_URL, fileName));
        claimDocumentRepository.save(claimDocument);
    }

    @Override
    @Transactional
    public ClaimResponseDTO cancelClaim(Long claimNo) {
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);

        return null;
    }

}
