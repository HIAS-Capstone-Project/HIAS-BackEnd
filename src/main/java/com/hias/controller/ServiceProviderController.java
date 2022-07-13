package com.hias.controller;

import com.hias.model.request.ServiceProviderRequestDTO;
import com.hias.model.response.ServiceProviderResponseDTO;
import com.hias.service.ServiceProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/service-provider/")
@AllArgsConstructor
@Slf4j
public class ServiceProviderController {
    private final ServiceProviderService serviceProviderService;

    @GetMapping("list")
    public ResponseEntity<List<ServiceProviderResponseDTO>> findServiceProvider(@RequestParam(required = false) String key, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex, @RequestParam(required = false, defaultValue = "serviceProviderName,asc") String[] sort) {
        return new ResponseEntity<>(serviceProviderService.findServiceProvider(key, pageIndex, pageSize, sort), HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteServiceProvider(@RequestParam Long serviceProviderNo) throws Exception {
        serviceProviderService.deleteServiceProviderByID(serviceProviderNo);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("find-detail")
    public ResponseEntity<ServiceProviderResponseDTO> findServiceProviderById(@RequestParam Long serviceProviderNo) {
        return new ResponseEntity<>(serviceProviderService.findServiceProviderByID(serviceProviderNo), HttpStatus.OK);
    }

    @PostMapping("save")
    public ResponseEntity<String> saveServiceProvider(@RequestBody ServiceProviderRequestDTO serviceProviderRequestDTO) {
        log.info(serviceProviderRequestDTO.toString());
        serviceProviderService.saveServiceProvider(serviceProviderRequestDTO);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
