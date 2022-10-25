package com.senla.advertisement.dto;

import com.senla.advertisement.entities.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PurchaseDTO {

    private int id;

    private LocalDateTime purchasedAt;

    private int count;

    private String purchaser;

    private String product;


    public PurchaseDTO(Purchase purchase) {
        this.id = purchase.getId();
        this.purchasedAt = purchase.getPurchasedAt();
        this.count = purchase.getCount();
        this.purchaser = purchase.getPurchaser().getUsername();
        this.product = purchase.getProduct().getName();
    }
}
