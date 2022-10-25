package com.senla.advertisement.dao;

import com.senla.advertisement.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseDAO extends JpaRepository<Purchase, Integer> {
    @Query(value = " select * from purchase " +
            "left join product on purchase.product_id = product.id " +
            "left join advertisement on product.ad_id = advertisement.id " +
            "where advertisement.user_id = ?1", nativeQuery = true)
    List<Purchase> getSalesHistoryById(int userId);
}
