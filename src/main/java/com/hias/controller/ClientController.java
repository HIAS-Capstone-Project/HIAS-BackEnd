package com.hias.controller;

import com.hias.constant.FieldNameConstant;
import com.hias.exception.HIASException;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponeDTO;
import com.hias.model.response.PagingResponseModel;
import com.hias.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/client/")
@AllArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @GetMapping("get-all")
    public ResponseEntity<List<ClientResponeDTO>> getAll() {
        return new ResponseEntity<>(clientService.getAll(), HttpStatus.OK);
    }

    @GetMapping("client-detail")
    public ResponseEntity<ClientResponeDTO> getDetail(@RequestParam Long clientNo) {
        return new ResponseEntity<>(clientService.getDetail(clientNo), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<PagingResponseModel<ClientResponeDTO>> search(@RequestParam(required = false) String searchValue,
                                                                        @PageableDefault(page = 0, size = 10)
                                                                        @SortDefault.SortDefaults({
                                                                                @SortDefault(sort = FieldNameConstant.MODIFIED_ON,
                                                                                        direction = Sort.Direction.DESC)
                                                                        }) Pageable pageable) {
        return new ResponseEntity<>(clientService.search(searchValue, pageable), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<ClientResponeDTO> create(@RequestBody ClientRequestDTO clientRequestDTO) throws HIASException {
        return new ResponseEntity<>(clientService.create(clientRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<ClientResponeDTO> update(@RequestBody ClientRequestDTO clientRequestDTO) {
        return new ResponseEntity<>(clientService.update(clientRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<ClientResponeDTO> delete(@RequestParam Long clientNo) {
        return new ResponseEntity<>(clientService.delete(clientNo), HttpStatus.OK);
    }

    @GetMapping("find-by-employee-no")
    public ResponseEntity<List<ClientResponeDTO>> findByEmployeeNo(@RequestParam Long employeeNo) {
        return new ResponseEntity<>(clientService.findByEmployeeNo(employeeNo), HttpStatus.OK);
    }
}
