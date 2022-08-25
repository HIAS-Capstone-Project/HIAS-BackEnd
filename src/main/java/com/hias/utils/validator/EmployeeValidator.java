package com.hias.utils.validator;

import com.hias.entity.Employee;
import com.hias.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    public boolean isEmployeeIDExistance(String employeeID) {
        List<Employee> employees = employeeRepository.findByEmployeeIDIgnoreCaseAndIsDeletedIsFalse(employeeID);
        if (CollectionUtils.isNotEmpty(employees)) {
            log.error("[isEmployeeIDExistance] employee id : {} has already exist.", employeeID);
            return true;
        }
        return false;
    }
}
