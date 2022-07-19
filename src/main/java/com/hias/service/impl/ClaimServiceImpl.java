package com.hias.service.impl;

import com.hias.entity.Claim;
import com.hias.mapper.response.ClaimResponseDTOMapper;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.repository.ClaimRepository;
import com.hias.service.ClaimService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimResponseDTOMapper claimResponseDTOMapper;

    @Override
    public List<ClaimResponseDTO> findAll() {
        List<Claim> claims = claimRepository.findAll();
        return claimResponseDTOMapper.toDtoList(claims);
    }
}
