package com.senla.advertisement.dao;

import com.senla.advertisement.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
}
