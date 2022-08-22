package com.hias.controller;


import com.hias.entity.District;
import com.hias.entity.Province;
import com.hias.entity.TempDis;
import com.hias.entity.TempPro;
import com.hias.model.response.BankResponseDTO;
import com.hias.repository.DistrictRepository;
import com.hias.repository.ProvinceRepository;
import com.hias.service.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/bank/")
@AllArgsConstructor
@Slf4j
public class BankController {

    private final BankService bankService;

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;

    @GetMapping("find-all")
    public ResponseEntity<List<BankResponseDTO>> findAll() {
        return new ResponseEntity<>(bankService.findAll(), HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity<String> insert() throws IOException {
        String url = "https://provinces.open-api.vn/api/?depth=2";
        RestTemplate restTemplate = new RestTemplate();
        TempPro[] list = restTemplate.getForObject(url, TempPro[].class);
        Province province;
        List<District> districts = new ArrayList<>();
        for (TempPro tp : list) {
            province = provinceRepository.save(Province.builder().provinceName(tp.getName()).build());
            for (TempDis td : tp.getDistricts()) {
                districts.add(District.builder().
                        provinceNo(province.getProvinceNo()).province(Province.builder().provinceNo(province.getProvinceNo()).build()).districtName(td.getName()).build());
            }
            districtRepository.saveAll(districts);
            districts.clear();
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
