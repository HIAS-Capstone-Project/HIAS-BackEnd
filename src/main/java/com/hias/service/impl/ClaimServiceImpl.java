package com.hias.service.impl;

import com.hias.entity.Claim;
import com.hias.mapper.request.ClaimRequestDTOMapper;
import com.hias.mapper.response.ClaimResponseDTOMapper;
import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.repository.ClaimRepository;
import com.hias.service.ClaimService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimRequestDTOMapper claimRequestDTOMapper;
    private final ClaimResponseDTOMapper claimResponseDTOMapper;

    @Override
    public List<ClaimResponseDTO> findAll() {
        List<Claim> claims = claimRepository.findAll();
        return claimResponseDTOMapper.toDtoList(claims);
    }

    @Override
    @Transactional
    public ClaimResponseDTO create(ClaimRequestDTO claimRequestDTO) {
        Claim claim = claimRequestDTOMapper.toEntity(claimRequestDTO);
        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        return claimResponseDTO;
    }
}
