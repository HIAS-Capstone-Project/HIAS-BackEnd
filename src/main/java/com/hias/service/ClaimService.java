package com.hias.service;

import com.hias.model.response.ClaimResponseDTO;

import java.util.List;

public interface ClaimService {

    public List<ClaimResponseDTO> findAll();
}
