package com.hias.service;

import com.hias.model.response.BankResponseDTO;

import java.util.List;

public interface BankService {

    List<BankResponseDTO> findAll();

}
