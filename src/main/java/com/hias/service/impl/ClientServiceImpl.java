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

        List<Client> clients = clientRepository.findByIsDeletedIsFalse();

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
     Client client = clientRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
        if (client !=null) {
            clientResponeDTO = clientResponeDTOMapper.toDto(client);
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
        Client client = clientRepository.findByClientNoAndIsDeletedIsFalse(clientRequestDTO.getClientNo());
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        if (client !=null) {
            client.setCorporateID(clientRequestDTO.getCorporateID());
            client.setName(clientRequestDTO.getName());
            clientResponeDTO = clientResponeDTOMapper.toDto(clientRepository.save(client));
        }
        return clientResponeDTO;
    }

    @Override
    public ClientResponeDTO deleteClient(Long clientNo) {
        log.info("[deleteClient] start delete client {}", clientNo);
        Client client = clientRepository.findByClientNoAndIsDeletedIsFalse(clientNo);
        ClientResponeDTO clientResponeDTO = new ClientResponeDTO();
        if (client !=null) {
            if (!client.isDeleted()) {
                client.setDeleted(true);
                clientResponeDTO = clientResponeDTOMapper.toDto(clientRepository.save(client));
            }
        }
        return clientResponeDTO;
    }
}
