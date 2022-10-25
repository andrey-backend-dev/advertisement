package com.senla.advertisement.dao;

import com.senla.advertisement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    Integer deleteByUsername(String username);


}