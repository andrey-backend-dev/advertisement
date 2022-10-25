package com.senla.advertisement.dao;

import com.senla.advertisement.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
