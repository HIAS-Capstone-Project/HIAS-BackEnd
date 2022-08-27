package com.hias.service;

import com.hias.model.response.ClaimRemarkHistoryResponseDTO;

import java.util.List;

public interface ClaimRemarkHistoryService {

    List<ClaimRemarkHistoryResponseDTO> findByClaimNo(Long claimNo);


}
