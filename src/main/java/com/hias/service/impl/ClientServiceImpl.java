package com.hias.service.impl;

import com.hias.entity.Client;
import com.hias.mapper.ClientRequestDTOMapper;
import com.hias.mapper.ClientResponeDTOMapper;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponeDTO;
import com.hias.repository.ClientRepository;
import com.hias.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientResponeDTOMapper clientResponeDTOMapper;
    private final ClientRequestDTOMapper clientRequestDTOMapper;

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

    @Override
    public ClientResponeDTO getDetail(Long clientNo) {
        log.info("[getDetail] start get detail client");
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        Optional<Client> client = clientRepository.findById(clientNo);
        if (client.isPresent()) {
            clientResponeDTO = clientResponeDTOMapper.toDto(client.get());

        }
        return clientResponeDTO;
    }

    @Override
    public ClientResponeDTO createClient(ClientRequestDTO clientRequestDTO) {
        log.info("[createClient] start get detail client");
        Client client = clientRepository.save(clientRequestDTOMapper.toEntity(clientRequestDTO));
        log.info("create client successfully");
        return clientResponeDTOMapper.toDto(client);
    }

    @Override
    public ClientResponeDTO updateClient(ClientRequestDTO clientRequestDTO) {
        log.info("[updateClient] update client");
        Optional<Client> optionalClient = clientRepository.findById(clientRequestDTO.getClientNo());
        Client client;
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        if (optionalClient.isPresent()) {
            client = optionalClient.get();
            client.setCorporateID(clientRequestDTO.getCorporateID());
            client.setName(clientRequestDTO.getName());
            clientResponeDTO = clientResponeDTOMapper.toDto(clientRepository.save(client));
        }
        return clientResponeDTO;
    }

    @Override
    public ClientResponeDTO deleteClient(Long clientNo) {
        log.info("[deleteClient] start delete client {}", clientNo);
        Optional<Client> optionalClient = clientRepository.findById(clientNo);
        Client client;
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        if (optionalClient.isPresent()) {
            client = optionalClient.get();
            if (!client.isDeleted()) {
                client.setDeleted(true);
                clientResponeDTO = clientResponeDTOMapper.toDto(clientRepository.save(client));
            }
        }
        return clientResponeDTO;
    }
}
