package com.hias.service.impl;

import com.hias.entity.Client;
import com.hias.mapper.ClientResponeDTOMapper;
import com.hias.model.response.ClientResponeDTO;
import com.hias.repository.ClientRepository;
import com.hias.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientResponeDTOMapper clientResponeDTOMapper;


    @Override
    public List<ClientResponeDTO> getAll() {
        log.info("[getAll] Start get all clients.");
        List<ClientResponeDTO> clientResponeDTOList = new ArrayList<>();

        List<Client> clients = clientRepository.findAll();

        if (!CollectionUtils.isEmpty(clients)) {
            log.info("[getAll] Size of clients : {}.", clients.size());
            clientResponeDTOList = clientResponeDTOMapper.toDtoList(clients);
        }
        return clientResponeDTOList;
    }
}
