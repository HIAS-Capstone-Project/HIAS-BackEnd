package com.hias.controller;

import com.hias.exception.HIASException;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponeDTO;
import com.hias.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("create")
    public ResponseEntity<ClientResponeDTO> create(@RequestBody ClientRequestDTO clientRequestDTODTO) throws HIASException {
        return new ResponseEntity<>(clientService.create(clientRequestDTODTO), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<ClientResponeDTO> update(@RequestBody ClientRequestDTO clientRequestDTODTO) {
        return new ResponseEntity<>(clientService.update(clientRequestDTODTO), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<ClientResponeDTO> delete(@RequestParam Long clientNo) {
        return new ResponseEntity<>(clientService.delete(clientNo), HttpStatus.OK);
    }


}
