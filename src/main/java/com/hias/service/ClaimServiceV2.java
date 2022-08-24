package com.hias.service;

import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;

public interface ClaimServiceV2 {


    ClaimResponseDTO submit(ClaimSubmitRequestDTO claimSubmitRequestDTO);

    ClaimResponseDTO saveDraft(ClaimSubmitRequestDTO claimSubmitRequestDTO);
}
