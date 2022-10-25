package com.senla.advertisement.dto;

import com.senla.advertisement.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDTO {

    private Integer id;

    private String content;

    private LocalDateTime addedAt;

    private String user;

    private String advertisement;


    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.addedAt = comment.getAddedAt();
        this.user = comment.getUser().getUsername();
        this.advertisement = comment.getAdvertisement().getName();
    }
}
