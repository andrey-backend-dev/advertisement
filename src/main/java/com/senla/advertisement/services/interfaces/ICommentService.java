package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.CommentRequestDTO;
import com.senla.advertisement.dto.ContentDTO;

import java.util.List;

public interface ICommentService {

    List<CommentDTO> getList();
    CommentDTO findById(int id);
    CommentDTO updateById(int id, ContentDTO contentDTO, String token);
    boolean deleteById(int id, String token);
    CommentDTO create(CommentRequestDTO commentDTO, String token);
}
