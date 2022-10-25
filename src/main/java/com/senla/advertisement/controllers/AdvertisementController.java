package com.senla.advertisement.controllers;

import com.senla.advertisement.dto.AdvertisementRequestDTO;
import com.senla.advertisement.dto.AdvertisementResponseDTO;
import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.ProductDTO;
import com.senla.advertisement.dto.StatusDTO;
import com.senla.advertisement.dto.UserDTO;
import com.senla.advertisement.dto.WeeksDTO;
import com.senla.advertisement.services.interfaces.IAdvertisementService;
import com.senla.advertisement.utils.maps.SortMethodMap;
import com.senla.advertisement.utils.wrappers.BooleanWrapper;
import com.senla.advertisement.utils.wrappers.NumWrapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {
    @Autowired
    private IAdvertisementService advertisementService;
    @Autowired
    private Logger logger;


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AdvertisementResponseDTO findById(@PathVariable("id") int id) {
        logger.info("Method 'findById' with parameters (id: {}) is called from Advertisement Controller at {}",
                id, LocalDateTime.now());
        return advertisementService.findById(id);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdvertisementResponseDTO create(@RequestBody AdvertisementRequestDTO advertisementRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'create' with parameters (name: {}, about: {}) is called from Advertisement Controller at {}",
                advertisementRequestDTO.getName(), advertisementRequestDTO.getAbout(), LocalDateTime.now());
        return advertisementService.create(advertisementRequestDTO, token);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BooleanWrapper deleteById(@PathVariable("id") int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'deleteById' with parameters (id: {}) is called from Advertisement Controller at {}",
                id, LocalDateTime.now());
        return new BooleanWrapper(advertisementService.deleteById(id, token));
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdvertisementResponseDTO> getList(@RequestParam(name = "sort", defaultValue = "null", required = false) String sorting,
                                                  @RequestParam(name = "order", defaultValue = "null", required = false) String orderName) {
        logger.info("Method 'getList' with parameters (sort: {}, order: {}) is called from Advertisement Controller at {}",
                sorting, orderName, LocalDateTime.now());
        return advertisementService.getList(SortMethodMap.getSortMethodByName(sorting), orderName, false);
    }

    @GetMapping(value = "/whole-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdvertisementResponseDTO> getWholeList(@RequestParam(name = "sort", defaultValue = "null", required = false) String sorting,
                                                  @RequestParam(name = "order", defaultValue = "null", required = false) String orderName) {
        logger.info("Method 'getWholeList' with parameters (sort: {}, order: {}) is called from Advertisement Controller at {}",
                sorting, orderName, LocalDateTime.now());
        return advertisementService.getList(SortMethodMap.getSortMethodByName(sorting), orderName, true);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AdvertisementResponseDTO> getListBySearch(@RequestParam(name = "search") String search) {
        logger.info("Method 'getListBySearch' with parameters (search: {}) is called from Advertisement Controller at {}",
                search, LocalDateTime.now());
        return advertisementService.getListBySearch(search);
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public NumWrapper count() {
        logger.info("Method 'count' is called from Advertisement Controller at {}", LocalDateTime.now());
        return new NumWrapper(advertisementService.count());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BooleanWrapper updateById(@PathVariable("id") int id, @RequestBody AdvertisementRequestDTO changedAdvertisement,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'updateById' with parameters (id: {}, name: {}, about: {}) is called from Advertisement Controller at {}",
                id, changedAdvertisement.getName(), changedAdvertisement.getAbout(), LocalDateTime.now());
        return new BooleanWrapper(advertisementService.updateById(id, changedAdvertisement, token));
    }

    @GetMapping(value = "/{id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserById(@PathVariable("id") int id) {
        logger.info("Method 'getUserById' with parameters (id: {}) is called from Advertisement Controller at {}",
                id, LocalDateTime.now());
        return advertisementService.getUserById(id);
    }

    @GetMapping(value = "/{id}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getProductsById(@PathVariable("id") int id) {
        logger.info("Method 'getProductById' with parameters (id: {}) is called from Advertisement Controller at {}",
                id, LocalDateTime.now());
        return advertisementService.getProductsById(id);
    }

    @GetMapping(value = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDTO> getCommentsById(@PathVariable("id") int id) {
        logger.info("Method 'getCommentsById' with parameters (id: {}) is called from Advertisement Controller at {}",
                id, LocalDateTime.now());
        return advertisementService.getCommentsById(id);
    }

    @PostMapping(value = "/{id}/promote", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdvertisementResponseDTO promoteAdvertisementById(@PathVariable("id") int id, @RequestBody WeeksDTO weeksDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'promoteAdvertisementById' with parameters (id: {}, weeks: {}) is called from Advertisement Controller at {}",
                id, weeksDTO.getWeeks(), LocalDateTime.now());
        return advertisementService.promoteAdvertisementById(id, weeksDTO.getWeeks(), token);
    }

    @PatchMapping(value = "/{id}/change-status", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdvertisementResponseDTO changeStatusById(@PathVariable("id") int id, @RequestBody StatusDTO statusDTO) {
        logger.info("Method 'changeStatusById' with parameters (id: {}, status: {}) is called from Advertisement Controller at {}",
                id, statusDTO.getStatus(), LocalDateTime.now());
        return advertisementService.changeStatusById(id, statusDTO.getStatus());
    }
}
