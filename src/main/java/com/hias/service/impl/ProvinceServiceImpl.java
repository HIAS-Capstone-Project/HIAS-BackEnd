package com.hias.service.impl;

import com.hias.entity.Province;
import com.hias.repository.ProvinceRepository;
import com.hias.service.ProvinceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        return null;
    }
}
