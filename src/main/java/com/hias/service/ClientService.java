package com.hias.service;

import com.hias.exception.HIASException;
import com.hias.model.request.ClientRequestDTO;
import com.hias.model.response.ClientResponseDTO;
import com.hias.model.response.PagingResponseModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

    List<ClientResponseDTO> getAll();

    ClientResponseDTO getDetail(Long clientNo);

    PagingResponseModel<ClientResponseDTO> search(String searchValue, Pageable pageable);

    ClientResponseDTO create(ClientRequestDTO clientRequestDTO) throws HIASException;

    ClientResponseDTO update(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO delete(Long clienNo);

    List<ClientResponseDTO> findByEmployeeNo(Long employeeNo);
}
