package com.senla.advertisement.dao;

import com.senla.advertisement.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDAO extends JpaRepository<Comment, Integer> {
}
