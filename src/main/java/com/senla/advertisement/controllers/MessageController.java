package com.senla.advertisement.controllers;

import com.senla.advertisement.dto.ContentDTO;
import com.senla.advertisement.dto.MessageDTO;
import com.senla.advertisement.dto.MessageRequestDTO;
import com.senla.advertisement.services.interfaces.IMessageService;
import com.senla.advertisement.utils.wrappers.BooleanWrapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;
    @Autowired
    private Logger logger;

    @DeleteMapping(value = "/{id}")
    public BooleanWrapper deleteById(@PathVariable("id") int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'deleteById' with parameters (id: {}) is called from Message Controller at {}",
                id, LocalDateTime.now());
        return new BooleanWrapper(messageService.deleteById(id, token));
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageDTO updateById(@PathVariable("id") int id, @RequestBody ContentDTO contentDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'updateById' with parameters (id: {}, content: {}) is called from Message Controller at {}",
                id, contentDTO.getContent(), LocalDateTime.now());
        return messageService.updateById(id, contentDTO, token);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageDTO create(@RequestBody MessageRequestDTO messageRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'create' with parameters (content: {}, receiver: {}) is called from Message Controller at {}",
                messageRequestDTO.getContent(), messageRequestDTO.getReceiver(), LocalDateTime.now());
        return messageService.create(messageRequestDTO, token);
    }
}
