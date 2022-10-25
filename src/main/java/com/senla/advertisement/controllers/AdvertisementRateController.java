package com.senla.advertisement.controllers;

import com.senla.advertisement.dto.AdvertisementRateRequestDTO;
import com.senla.advertisement.dto.AdvertisementRateResponseDTO;
import com.senla.advertisement.services.interfaces.IAdvertisementRateService;
import com.senla.advertisement.utils.wrappers.BooleanWrapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/advertisement-rates")
public class AdvertisementRateController {

    @Autowired
    private IAdvertisementRateService advertisementRateService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdvertisementRateResponseDTO> getList() {
        logger.info("Method 'getList' is called from AdvertisementRate Controller at {}", LocalDateTime.now());
        return advertisementRateService.getList();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdvertisementRateResponseDTO create(@RequestBody AdvertisementRateRequestDTO advertisementRateDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'create' with parameters (value: {}, adId: {}) is called from AdvertisementRate Controller at {}",
                advertisementRateDTO.getValue(), advertisementRateDTO.getAdId(), LocalDateTime.now());
        return advertisementRateService.create(advertisementRateDTO, token);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooleanWrapper deleteById(@PathVariable("id") int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'deleteById' with parameters (id: {}) is called from AdvertisementRate Controller at {}",
                id, LocalDateTime.now());
        return new BooleanWrapper(advertisementRateService.deleteById(id, token));
    }
}
