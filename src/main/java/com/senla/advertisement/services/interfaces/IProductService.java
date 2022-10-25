package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.dto.ProductDTO;
import com.senla.advertisement.dto.ProductRequestDTO;

import java.util.List;

public interface IProductService {
    ProductDTO findById(int id);
    List<ProductDTO> getList();
    ProductDTO create(ProductRequestDTO productDTO, String token);
    boolean deleteById(int id, String username);
    ProductDTO updateById(int id, ProductRequestDTO productDTO, String token);
}
