package com.hias.service;

import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClaimService {

    List<ClaimResponseDTO> findAll();

    ClaimResponseDTO create(ClaimRequestDTO claimRequestDTO);

    ClaimResponseDTO update(ClaimRequestDTO claimRequestDTO);

    ClaimResponseDTO deleteByClaimNo(Long claimNo);

    ClaimResponseDTO submitByMember(Long memberNo, ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files);
}
