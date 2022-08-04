package com.hias.controller;


import com.hias.constant.CommonConstant;
import com.hias.constant.FieldNameConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.EmployeeRequestDTO;
import com.hias.model.response.EmployeeResponseDTO;
import com.hias.model.response.MemberResponseDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employee/")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("list")
    public ResponseEntity findEmployee(@RequestParam(required = false) String key, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false, defaultValue = "employeeName,asc") String[] sort) {
        return new ResponseEntity<>(employeeService.findEmployee(key, pageIndex, pageSize, sort), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<PagingResponseModel<EmployeeResponseDTO>> search(@RequestParam(required = false) String searchValue,
                                                                           @PageableDefault(page = 0, size = 10)
                                                                           @SortDefault.SortDefaults({
                                                                                   @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                                                                                           direction = Sort.Direction.DESC)
                                                                           }) Pageable pageable) {
        return new ResponseEntity<>(employeeService.search(searchValue, pageable), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteEmployee(@RequestParam Long employeeNo) throws HIASException {
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

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody EmployeeRequestDTO employeeRequestDTO) throws HIASException {
        employeeService.createEmployee(employeeRequestDTO);
        return new ResponseEntity<>(CommonConstant.CREATED_SUCCESSFULLY, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        employeeService.updateEmployee(employeeRequestDTO);
        return new ResponseEntity<>(CommonConstant.UPDATED_SUCCESSFULLY, HttpStatus.OK);
    }
}
