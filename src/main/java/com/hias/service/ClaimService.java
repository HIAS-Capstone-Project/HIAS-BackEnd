package com.hias.service;

import com.hias.exception.HIASException;
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

    ClaimResponseDTO submitForMember(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException;

    ClaimResponseDTO saveDraftForMember(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException, HIASException;

    ClaimResponseDTO cancelClaim(Long claimNo) throws HIASException;

    ClaimResponseDTO businessApproval(Long claimNo) throws HIASException;

    ClaimResponseDTO startProgress(Long claimNo) throws HIASException;
}
