package com.hias.controller;


import com.hias.model.request.EmployeeRequestDTO;
import com.hias.model.response.EmployeeResponseDTO;
import com.hias.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee/")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("list")
    public ResponseEntity<List<EmployeeResponseDTO>> findEmployee(@RequestParam(required = false) String key, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false, defaultValue = "employeeName,asc") String[] sort) {
        return new ResponseEntity<>(employeeService.findEmployee(key, pageIndex, pageSize, sort), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteEmployee(@RequestParam Long employeeNo) throws Exception {
        employeeService.deleteEmployeeByEmployeeNo(employeeNo);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("find-detail")
    public ResponseEntity<EmployeeResponseDTO> findEmployeeById(@RequestParam Long employeeNo) {
        return new ResponseEntity<>(employeeService.findEmployeeByEmployeeNo(employeeNo), HttpStatus.OK);
    }

    @PostMapping("save")
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        log.info(employeeRequestDTO.toString());
        employeeService.saveEmployee(employeeRequestDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
