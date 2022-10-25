package com.senla.advertisement.services;

import com.senla.advertisement.dao.ProductDAO;
import com.senla.advertisement.dao.PurchaseDAO;
import com.senla.advertisement.dao.UserDAO;
import com.senla.advertisement.dto.PurchaseDTO;
import com.senla.advertisement.dto.PurchaseRequestDTO;
import com.senla.advertisement.entities.Product;
import com.senla.advertisement.entities.Purchase;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtProvider;
import com.senla.advertisement.services.interfaces.IPurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchaseService implements IPurchaseService {

    @Autowired
    private PurchaseDAO purchaseDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private ProductDAO productDAO;


    @Override
    @Transactional(readOnly = true)
    public PurchaseDTO findById(int id) {
        return new PurchaseDTO(purchaseDAO.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public PurchaseDTO create(PurchaseRequestDTO purchaseDTO, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        Product product = productDAO.findById(purchaseDTO.getProductId()).orElse(null);
        int count = purchaseDTO.getCount();
        if (user.getMoney() >= product.getCost() * count && count <= product.getAvailable()) {
            Purchase purchase = new Purchase();
            purchase.setCount(count);
            purchase.setPurchaser(user);
            purchase.setProduct(product);
            purchase.setPurchasedAt(LocalDateTime.now());

            user.setMoney(user.getMoney() - product.getCost() * count);
            product.setAvailable(product.getAvailable() - count);

            return new PurchaseDTO(purchaseDAO.save(purchase));
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getList() {
        return purchaseDAO.findAll().stream().map(PurchaseDTO::new).toList();
    }
}
