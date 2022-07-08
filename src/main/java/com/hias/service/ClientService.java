package com.hias.service;

import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponeDTO;

import java.util.List;

public interface ClientService {

    List<ClientResponeDTO> getAll();

    ClientResponeDTO getDetail(Long clientNo);

    ClientResponeDTO createClient(ClientRequestDTO clientRequestDTO);

    ClientResponeDTO updateClient(ClientRequestDTO clientRequestDTO);

    ClientResponeDTO deleteClient(Long clienNo);
}
