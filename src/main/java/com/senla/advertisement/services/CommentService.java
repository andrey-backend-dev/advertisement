package com.senla.advertisement.services;

import com.senla.advertisement.dao.AdvertisementDAO;
import com.senla.advertisement.dao.CommentDAO;
import com.senla.advertisement.dao.UserDAO;
import com.senla.advertisement.dto.CommentDTO;
import com.senla.advertisement.dto.CommentRequestDTO;
import com.senla.advertisement.dto.ContentDTO;
import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.entities.Comment;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtProvider;
import com.senla.advertisement.services.interfaces.ICommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private AdvertisementDAO advertisementDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getList() {
        return commentDAO.findAll().stream().map(CommentDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDTO findById(int id) {
        return new CommentDTO(commentDAO.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public boolean deleteById(int id, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Comment comment = commentDAO.findById(id).orElse(null);

        if (user == comment.getUser()) {
            commentDAO.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public CommentDTO updateById(int id, ContentDTO contentDTO, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Comment comment = commentDAO.findById(id).orElse(null);

        if (user == comment.getUser()) {

            if (contentDTO.getContent() != null && !contentDTO.getContent().equals(comment.getContent())) {
                comment.setContent(contentDTO.getContent());
            }

            return new CommentDTO(comment);
        }

        return null;
    }

    @Override
    @Transactional
    public CommentDTO create(CommentRequestDTO commentDTO, String token) {
        Advertisement advertisement = advertisementDAO.findById(commentDTO.getAdId()).orElse(null);
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setAdvertisement(advertisement);
        comment.setUser(user);
        comment.setAddedAt(LocalDateTime.now());
        return new CommentDTO(commentDAO.save(comment));
    }
}
