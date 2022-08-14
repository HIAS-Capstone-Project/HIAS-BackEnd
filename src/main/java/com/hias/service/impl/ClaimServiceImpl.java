package com.hias.service.impl;

import com.hias.constant.CommonConstant;
import com.hias.constant.DateConstant;
import com.hias.constant.FireBaseConstant;
import com.hias.constant.StatusCode;
import com.hias.entity.Claim;
import com.hias.entity.ClaimDocument;
import com.hias.entity.License;
import com.hias.mapper.request.ClaimRequestDTOMapper;
import com.hias.mapper.request.ClaimSubmitRequestDTOMapper;
import com.hias.mapper.response.ClaimResponseDTOMapper;
import com.hias.model.request.ClaimRequestDTO;
import com.hias.model.request.ClaimSubmitRequestDTO;
import com.hias.model.response.ClaimResponseDTO;
import com.hias.repository.ClaimDocumentRepository;
import com.hias.repository.ClaimRepository;
import com.hias.service.ClaimService;
import com.hias.utils.DateUtils;
import com.hias.utils.FireBaseUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimDocumentRepository claimDocumentRepository;
    private final ClaimRequestDTOMapper claimRequestDTOMapper;
    private final ClaimSubmitRequestDTOMapper claimSubmitRequestDTOMapper;
    private final ClaimResponseDTOMapper claimResponseDTOMapper;
    private final FireBaseUtils fireBaseUtils;

    @Override
    public List<ClaimResponseDTO> findAll() {
        List<Claim> claims = claimRepository.findAllByIsDeletedIsFalse();
        return claimResponseDTOMapper.toDtoList(claims);
    }

    @Override
    @Transactional
    public ClaimResponseDTO create(ClaimRequestDTO claimRequestDTO) {
        Claim claim = claimRequestDTOMapper.toEntity(claimRequestDTO);
        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO update(ClaimRequestDTO claimRequestDTO) {
        Claim claim = claimRequestDTOMapper.toEntity(claimRequestDTO);
        ClaimResponseDTO claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO deleteByClaimNo(Long claimNo) {
        ClaimResponseDTO claimResponseDTO = new ClaimResponseDTO();
        Optional<Claim> claimOptional = claimRepository.findByClaimNoAndIsDeletedIsFalse(claimNo);
        if (!claimOptional.isPresent()) {
            log.info("[deleteByBenefitNo] Cannot found claim with claimNo : {} in the system.", claimNo);
        } else {
            Claim claim = claimOptional.get();
            claim.setDeleted(Boolean.TRUE);
            claimResponseDTO = claimResponseDTOMapper.toDto(claimRepository.save(claim));
            log.info("[deleteByBenefitNo] Delete claim with claimNo : {} in the system.", claimNo);
        }
        return claimResponseDTO;
    }

    @Override
    @Transactional
    public ClaimResponseDTO submitByMember(Long memberNo, ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) {

        return null;
    }

    @Override
    @Transactional
    public ClaimResponseDTO saveDraftForMember(ClaimSubmitRequestDTO claimSubmitRequestDTO, List<MultipartFile> files) throws IOException {
        Claim claim = claimSubmitRequestDTOMapper.toEntity(claimSubmitRequestDTO);
        claim.setStatusCode(StatusCode.DRAFT);
        Claim claimCreated = claimRepository.save(claim);

        String fileName, extension, originalFileName;
        MultipartFile file;
        List<Long> licenseNos = claimSubmitRequestDTO.getLicenseNos();
        int size = Math.min(files.size(), licenseNos.size());
        for (int index = 0; index < size; index++) {
            file = files.get(index);
            originalFileName = file.getOriginalFilename().trim();
            fileName = originalFileName.replaceAll(CommonConstant.REGEX_FILE_EXTENSION, StringUtils.EMPTY);
            extension = org.springframework.util.StringUtils.getFilenameExtension(originalFileName);
            fileName = fileName + DateUtils.currentDateTimeAsString(DateConstant.DD_MM_YYYY_HH_MM_SS_SSS) + CommonConstant.DOT + extension;
            fireBaseUtils.uploadFile(file, fileName);

            saveClaimDocument(claimCreated, fileName, licenseNos, index);
        }

        return null;
    }

    private void saveClaimDocument(Claim claimCreated, String fileName, List<Long> licenseNos, int index) {
        ClaimDocument claimDocument = new ClaimDocument();
        claimDocument.setClaimNo(claimCreated.getClaimNo());
        claimDocument.setClaim(claimCreated);
        claimDocument.setClaimNo(claimCreated.getClaimNo());
        claimDocument.setLicense(License.builder().licenseNo(licenseNos.get(index)).build());
        claimDocument.setLicenseNo(licenseNos.get(index));
        claimDocument.setPathFile(fileName);
        claimDocument.setFileUrl(String.format(FireBaseConstant.FILE_URL, fileName));
        claimDocumentRepository.save(claimDocument);
    }

}
