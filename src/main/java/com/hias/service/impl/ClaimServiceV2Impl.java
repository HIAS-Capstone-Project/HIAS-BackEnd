package com.hias.service.impl;

import com.hias.constant.ActionType;
import com.hias.constant.MessageCode;
import com.hias.constant.RecordSource;
import com.hias.constant.StatusCode;
import com.hias.entity.Claim;
import com.hias.entity.ClaimDocument;
import com.hias.entity.ClaimRemarkHistory;
import com.hias.entity.License;
import com.hias.mapper.request.ClaimSubmitRequestDTOMapper;
import com.hias.mapper.response.ClaimResponseDTOMapper;
import com.hias.model.request.ClaimDocumentRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.repository.ClaimDocumentRepository;
import com.hias.repository.ClaimRemarkHistoryRepository;
import com.hias.repository.ClaimRepository;
import com.hias.repository.EmployeeRepository;
import com.hias.service.ClaimServiceV2;
import com.hias.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClaimServiceV2Impl implements ClaimServiceV2 {

    private final ClaimRepository claimRepository;
    private final ClaimDocumentRepository claimDocumentRepository;
    private final ClaimRemarkHistoryRepository claimRemarkHistoryRepository;
    private final EmployeeRepository employeeRepository;

    private final ClaimSubmitRequestDTOMapper claimSubmitRequestDTOMapper;
    private final ClaimResponseDTOMapper claimResponseDTOMapper;
    private final MessageUtils messageUtils;

    @Override
    @Transactional
    public ClaimResponseDTO submit(ClaimSubmitRequestDTO claimSubmitRequestDTO) {
        Long claimNo = claimSubmitRequestDTO.getClaimNo();
        ClaimResponseDTO claimResponseDTO;
        Claim claim;
        StatusCode fromStatusCode, toStatusCode;
        ActionType actionType = ActionType.SUBMIT;
        if (claimNo != null) {
            claim = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo).get();
            fromStatusCode = claim.getStatusCode();
            toStatusCode = StatusCode.SUBMITTED;
            if (!StatusCode.DRAFT.equals(claim.getStatusCode())) {
                actionType = ActionType.RE_SUBMIT;
            }
            this.mergeClaimRequest(claimSubmitRequestDTO, claim);
        } else {
            claim = claimSubmitRequestDTOMapper.toEntity(claimSubmitRequestDTO);
            claim.setRecordSource((claimSubmitRequestDTO.getServiceProviderNo() == null) ?
                    RecordSource.M : RecordSource.SVP);
            fromStatusCode = toStatusCode = StatusCode.SUBMITTED;
        }
        claim.setStatusCode(StatusCode.SUBMITTED);
        claim.setSubmittedDate(LocalDateTime.now());
        Claim claimSaved = claimRepository.save(claim);
        Long claimNoSaved = claimSaved.getClaimNo();

        processForClaimDocuments(claimSubmitRequestDTO, claimNoSaved);

        processRemarkHistoryForSubmittedClaim(claimSaved, fromStatusCode, toStatusCode, actionType);

        if (claim.getBusinessAppraisalBy() == null) {
            List<Long> employeeNos = employeeRepository.findBusinessAppraiserHasClaimAtLeast();
            if (CollectionUtils.isNotEmpty(employeeNos)) {
                claim.setBusinessAppraisalBy(employeeNos.get(0));
                claim = claimRepository.save(claim);
            }
        }

        claimResponseDTO = claimResponseDTOMapper.toDto(claim);
        return claimResponseDTO;
    }

    private void processRemarkHistoryForSubmittedClaim(Claim claim, StatusCode fromStatusCode, StatusCode toStatusCode, ActionType actionType) {
        ClaimRemarkHistory claimRemarkHistory = new ClaimRemarkHistory();
        claimRemarkHistory.setClaim(claim);
        claimRemarkHistory.setFromStatusCode(fromStatusCode);
        claimRemarkHistory.setToStatusCode(toStatusCode);
        claimRemarkHistory.setActionType(actionType);
        claimRemarkHistory.setRemark(messageUtils.getMessage(MessageCode.CL_REMARK_002, claim.getClaimID()));
        claimRemarkHistoryRepository.save(claimRemarkHistory);
    }

    @Override
    @Transactional
    public ClaimResponseDTO saveDraft(ClaimSubmitRequestDTO claimSubmitRequestDTO) {
        Long claimNo = claimSubmitRequestDTO.getClaimNo();
        ClaimResponseDTO claimResponseDTO;
        Claim claim;
        StatusCode fromStatusCode;
        StatusCode toStatusCode;
        if (claimNo != null) {
            claim = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo).get();
            fromStatusCode = toStatusCode = claim.getStatusCode();
            this.mergeClaimRequest(claimSubmitRequestDTO, claim);
        } else {
            claim = claimSubmitRequestDTOMapper.toEntity(claimSubmitRequestDTO);
            claim.setStatusCode(StatusCode.DRAFT);
            claim.setRecordSource((claimSubmitRequestDTO.getServiceProviderNo() == null) ?
                    RecordSource.M : RecordSource.SVP);
            fromStatusCode = toStatusCode = StatusCode.DRAFT;
        }
        Claim claimSaved = claimRepository.save(claim);
        Long claimNoSaved = claimSaved.getClaimNo();

        processForClaimDocuments(claimSubmitRequestDTO, claimNoSaved);

        processRemarkHistoryForDraftClaim(claimSaved, fromStatusCode, toStatusCode);

        claimResponseDTO = claimResponseDTOMapper.toDto(claim);
        return claimResponseDTO;
    }

    private void processRemarkHistoryForDraftClaim(Claim claim, StatusCode fromStatusCode, StatusCode toStatusCode) {
        ClaimRemarkHistory claimRemarkHistory = new ClaimRemarkHistory();
        claimRemarkHistory.setClaim(claim);
        claimRemarkHistory.setFromStatusCode(fromStatusCode);
        claimRemarkHistory.setToStatusCode(toStatusCode);
        claimRemarkHistory.setActionType(ActionType.SAVE_DRAFT);
        claimRemarkHistory.setRemark(messageUtils.getMessage(MessageCode.CL_REMARK_001, claim.getClaimID()));
        claimRemarkHistoryRepository.save(claimRemarkHistory);
    }

    private void processForClaimDocuments(ClaimSubmitRequestDTO claimSubmitRequestDTO, Long claimNoSaved) {
        claimDocumentRepository.deleteByClaimNoAndIsDeletedIsFalse(claimNoSaved);
        List<ClaimDocumentRequestDTO> claimDocumentRequestDTOS = claimSubmitRequestDTO.getClaimDocumentRequestDTOS();
        List<ClaimDocument> claimDocuments = new ArrayList<>();
        Long licenseNo;
        int size;
        List<String> fileUrls;
        List<String> fileNames;
        for (ClaimDocumentRequestDTO claimDocumentRequestDTO : claimDocumentRequestDTOS) {
            licenseNo = claimDocumentRequestDTO.getLicenseNo();

            fileUrls = claimDocumentRequestDTO.getFileUrls();
            fileNames = claimDocumentRequestDTO.getFileNames();
            size = Math.min(fileUrls.size(), fileNames.size());

            for (int index = 0; index < size; index++) {
                ClaimDocument claimDocument = new ClaimDocument();
                claimDocument.setLicense(License.builder().licenseNo(licenseNo).build());
                claimDocument.setFileName(fileNames.get(index));
                claimDocument.setOriginalFileName(fileNames.get(index));
                claimDocument.setPathFile(fileNames.get(index));
                claimDocument.setFileUrl(fileUrls.get(index));
                claimDocument.setClaim(Claim.builder().claimNo(claimNoSaved).build());
                claimDocuments.add(claimDocument);
            }
        }
        claimDocumentRepository.saveAll(claimDocuments);
    }

    private void mergeClaimRequest(ClaimSubmitRequestDTO claimSubmitRequestDTO, Claim claim) {
        claim.setVisitDate(claimSubmitRequestDTO.getVisitDate());
        claim.setAdmissionFromDate(claimSubmitRequestDTO.getAdmissionFromDate());
        claim.setAdmissionToDate(claimSubmitRequestDTO.getAdmissionToDate());
        claim.setDescription(claimSubmitRequestDTO.getDescription());
        claim.setMedicalAddress(claimSubmitRequestDTO.getMedicalAddress());
        claim.setClaimAmount(claimSubmitRequestDTO.getClaimAmount());
        claim.setBenefitNo(claimSubmitRequestDTO.getBenefitNo());
    }
}
