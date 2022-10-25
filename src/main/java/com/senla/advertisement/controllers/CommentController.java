package com.senla.advertisement.controllers;

import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.CommentRequestDTO;
import com.senla.advertisement.dto.ContentDTO;
import com.senla.advertisement.services.interfaces.ICommentService;
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
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CommentDTO> getList() {
        logger.info("Method 'getList' is called from Comment Controller at {}", LocalDateTime.now());
        return commentService.getList();
    }

    @GetMapping(value = "/{id}")
    public CommentDTO findById(@PathVariable("id") int id) {
        logger.info("Method 'findById' with parameters (id: {}) is called from Comment Controller at {}",
                id, LocalDateTime.now());
        return commentService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public BooleanWrapper deleteById(@PathVariable("id") int id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'deleteById' with parameters (id: {}) is called from Comment Controller at {}",
                id, LocalDateTime.now());
        return new BooleanWrapper(commentService.deleteById(id, token));
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO updateById(@PathVariable("id") int id, @RequestBody ContentDTO contentDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'updateById' with parameters (id: {}, content: {}) is called from Comment Controller at {}",
                id, contentDTO.getContent(), LocalDateTime.now());
        return commentService.updateById(id, contentDTO, token);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO create(@RequestBody CommentRequestDTO commentDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.split(" ")[1];
        logger.info("Method 'create' with parameters (adId: {}, content: {}) is called from Comment Controller at {}",
                commentDTO.getAdId(), commentDTO.getContent(), LocalDateTime.now());
        return commentService.create(commentDTO, token);
    }
}
