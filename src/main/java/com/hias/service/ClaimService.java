package com.hias.service;

import com.hias.exception.HIASException;
import com.hias.model.request.ClaimPaymentRequestDTO;
import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.model.response.PagingResponseModel;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClaimService {

    List<ClaimResponseDTO> findAll();

    ClaimResponseDTO viewDetail(Long claimNo);

    PagingResponseModel<ClaimResponseDTO> search(String searchValue, Pageable pageable);

    ClaimResponseDTO create(ClaimRequestDTO claimRequestDTO);

    ClaimResponseDTO update(ClaimRequestDTO claimRequestDTO);

    ClaimResponseDTO deleteByClaimNo(Long claimNo);

    ClaimResponseDTO submit(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException;

    ClaimResponseDTO saveDraft(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException;

    ClaimResponseDTO cancelClaim(Long claimNo) throws HIASException;

    ClaimResponseDTO businessVerified(Long claimNo);

    ClaimResponseDTO medicalVerified(Long claimNo);

    ClaimResponseDTO startProgress(Long claimNo);

    ClaimResponseDTO approve(Long claimNo);

    ClaimResponseDTO settleClaim(ClaimPaymentRequestDTO claimPaymentRequestDTO);
}
