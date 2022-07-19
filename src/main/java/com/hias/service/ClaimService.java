package com.hias.service;

import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.response.ClaimResponseDTO;

import java.util.List;

public interface ClaimService {

    List<ClaimResponseDTO> findAll();

    ClaimResponseDTO create(ClaimRequestDTO claimRequestDTO);
}
