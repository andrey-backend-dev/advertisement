package com.senla.advertisement.services;

import com.senla.advertisement.dao.AdvertisementDAO;
import com.senla.advertisement.dao.ProductDAO;
import com.senla.advertisement.dao.UserDAO;
import com.senla.advertisement.dto.ProductDTO;
import com.senla.advertisement.dto.ProductRequestDTO;
import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.entities.Product;
import com.senla.advertisement.entities.Role;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtProvider;
import com.senla.advertisement.services.interfaces.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AdvertisementDAO advertisementDAO;
    @Autowired
    private JwtProvider jwtProvider;


    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(int id) {
        return new ProductDTO(productDAO.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getList() {
        return productDAO.findAll().stream().map(ProductDTO::new).toList();
    }

    @Override
    @Transactional
    public ProductDTO create(ProductRequestDTO productDTO, String token) {
        Advertisement advertisement = advertisementDAO.findById(productDTO.getAdId()).orElse(null);
        if (advertisement.getUser() == userDAO.findByUsername(jwtProvider.getUsername(token))) {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setCost(productDTO.getCost());
            product.setAvailable(productDTO.getAvailable());
            product.setAdvertisement(advertisement);
            product = productDAO.save(product);
            return new ProductDTO(product);
        }
        return null;
    }

    @Override
    @Transactional
    public ProductDTO updateById(int id, ProductRequestDTO productDTO, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Product product = productDAO.findById(id).orElse(null);

        if (product.getAdvertisement().getUser() == user) {
            if (productDTO.getName() != null && !productDTO.getName().equals(product.getName()))
                product.setName(productDTO.getName());
            if (productDTO.getCost() != null && !productDTO.getCost().equals(product.getCost()))
                product.setCost(productDTO.getCost());
            if (productDTO.getAvailable() != null && !productDTO.getAvailable().equals(product.getAvailable()))
                product.setAvailable(productDTO.getAvailable());

            return new ProductDTO(product);
        }

        return null;
    }

    @Override
    @Transactional
    public boolean deleteById(int id, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Advertisement advertisement = productDAO.findById(id).orElse(null).getAdvertisement();

        if (advertisement.getUser() == user ||
                user.getRoles().stream().map(Role::getName).toList().contains("MODERATOR") ||
                user.getRoles().stream().map(Role::getName).toList().contains("ADMIN")) {
            productDAO.deleteById(id);
            return true;
        }

        return false;
    }
}
