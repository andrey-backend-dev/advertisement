package com.senla.advertisement.controllers;

import com.senla.advertisement.dto.ProductDTO;
import com.senla.advertisement.dto.ProductRequestDTO;
import com.senla.advertisement.services.interfaces.IProductService;
import com.senla.advertisement.utils.wrappers.BooleanWrapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO findById(@PathVariable("id") int id) {
        logger.info("Method 'findById' with parameters (id: {}) is called from Product Controller at {}",
                id, LocalDateTime.now());
        return productService.findById(id);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getList() {
        logger.info("Method 'getList' is called from Product Controller at {}", LocalDateTime.now());
        return productService.getList();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO create(@RequestBody ProductRequestDTO productDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'create' with parameters (adId: {}, name: {}, cost: {}, available: {}) is called from Product Controller at {}",
                productDTO.getAdId(), productDTO.getName(), productDTO.getCost(), productDTO.getAvailable(), LocalDateTime.now());
        return productService.create(productDTO, token);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO updateById(@PathVariable ("id") int id, @RequestBody ProductRequestDTO productDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'updateById' with parameters (id: {}, adId: {}, name: {}, cost: {}, available: {}) is called from Product Controller at {}",
                id, productDTO.getAdId(), productDTO.getName(), productDTO.getCost(), productDTO.getAvailable(), LocalDateTime.now());
        return productService.updateById(id, productDTO, token);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooleanWrapper deleteById(@PathVariable("id") int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'deleteById' with parameters (id: {}) is called from Product Controller at {}",
                id, LocalDateTime.now());
        return new BooleanWrapper(productService.deleteById(id, token));
    }
}
