package com.hias.service.impl;

import com.hias.entity.ClaimRemarkHistory;
import com.hias.entity.Employee;
import com.hias.mapper.response.ClaimRemarkHistoryResponseDTOMapper;
import com.hias.mapper.response.EmployeeResponseDTOMapper;
import com.hias.mapper.response.MemberResponseDTOMapper;
import com.hias.model.response.ClaimRemarkHistoryResponseDTO;
import com.hias.repository.ClaimRemarkHistoryRepository;
import com.hias.repository.EmployeeRepository;
import com.hias.service.ClaimRemarkHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class ClaimRemarkHistoryServiceImpl implements ClaimRemarkHistoryService {

    private final ClaimRemarkHistoryResponseDTOMapper claimRemarkHistoryResponseDTOMapper;
    private final EmployeeResponseDTOMapper employeeResponseDTOMapper;
    private final MemberResponseDTOMapper memberResponseDTOMapper;
    private final ClaimRemarkHistoryRepository claimRemarkHistoryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<ClaimRemarkHistoryResponseDTO> findByClaimNo(Long claimNo) {
        List<ClaimRemarkHistoryResponseDTO> claimRemarkHistoryResponseDTOS = new ArrayList<>();
        List<ClaimRemarkHistory> claimRemarkHistories = claimRemarkHistoryRepository.findByClaimNoAndIsDeletedIsFalseOrderByModifiedOnDesc(claimNo);
        Set<Long> employeeNos = claimRemarkHistories
                .stream()
                .map(ClaimRemarkHistory::getEmployeeNo)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Employee> employees = employeeRepository.findByEmployeeNoIn(employeeNos);

        Long employeeNo;
        Map<Long, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getEmployeeNo, Function.identity()));
        for (ClaimRemarkHistory claimRemarkHistory : claimRemarkHistories) {
            ClaimRemarkHistoryResponseDTO claimRemarkHistoryResponseDTO = claimRemarkHistoryResponseDTOMapper.toDto(claimRemarkHistory);
            claimRemarkHistoryResponseDTO.setMemberResponseDTO(memberResponseDTOMapper.toDto(claimRemarkHistory.getClaim().getMember()));
            employeeNo = claimRemarkHistory.getEmployeeNo();
            if (employeeNo != null) {
                claimRemarkHistoryResponseDTO.setEmployeeResponseDTO(employeeResponseDTOMapper.toDto(employeeMap.get(employeeNo)));
            }
            claimRemarkHistoryResponseDTOS.add(claimRemarkHistoryResponseDTO);
        }
        return claimRemarkHistoryResponseDTOS;
    }
}
