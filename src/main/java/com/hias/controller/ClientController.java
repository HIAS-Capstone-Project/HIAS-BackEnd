package com.hias.controller;

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

    @PostMapping("create-client")
    public ResponseEntity<ClientResponeDTO> createPolicy(@RequestBody ClientRequestDTO clientRequestDTODTO) {
        return new ResponseEntity<>(clientService.createClient(clientRequestDTODTO), HttpStatus.OK);
    }

    @PatchMapping("update-client")
    public ResponseEntity<ClientResponeDTO> updatePolicy(@RequestBody ClientRequestDTO clientRequestDTODTO) {
        return new ResponseEntity<>(clientService.updateClient(clientRequestDTODTO), HttpStatus.OK);
    }

    @DeleteMapping("detele-client")
    public ResponseEntity<ClientResponeDTO> deletePolicy(@RequestParam Long clientNo) {
        return new ResponseEntity<>(clientService.deleteClient(clientNo), HttpStatus.OK);
    }


}
