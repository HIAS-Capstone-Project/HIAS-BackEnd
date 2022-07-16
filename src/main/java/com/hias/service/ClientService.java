package com.hias.service;

import com.hias.exception.HIASException;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponeDTO;

import java.util.List;

public interface ClientService {

    List<ClientResponeDTO> getAll();

    ClientResponeDTO getDetail(Long clientNo);

    ClientResponeDTO create(ClientRequestDTO clientRequestDTO) throws HIASException;

    ClientResponeDTO update(ClientRequestDTO clientRequestDTO);

    ClientResponeDTO delete(Long clienNo);
}
