package com.senla.advertisement.controllers;

import com.senla.advertisement.dto.PurchaseDTO;
import com.senla.advertisement.dto.PurchaseRequestDTO;
import com.senla.advertisement.services.interfaces.IPurchaseService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private IPurchaseService purchaseService;
    @Autowired
    private Logger logger;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseDTO findById(@PathVariable("id") int id) {
        logger.info("Method 'findById' with parameters (id: {}) is called from Purchase Controller at {}",
                id, LocalDateTime.now());
        return purchaseService.findById(id);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseDTO> getList() {
        logger.info("Method 'getList' is called from Purchase Controller at {}", LocalDateTime.now());
        return purchaseService.getList();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseDTO create(@RequestBody PurchaseRequestDTO purchaseRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'findById' with parameters (productId: {}, count: {}) is called from Purchase Controller at {}",
                purchaseRequestDTO.getProductId(), purchaseRequestDTO.getCount(), LocalDateTime.now());
        return purchaseService.create(purchaseRequestDTO, token);
    }
}