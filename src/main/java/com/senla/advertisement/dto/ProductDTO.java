package com.senla.advertisement.dto;

import com.senla.advertisement.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductDTO {

    private Integer id;

    private String name;

    private Float cost;

    private Integer available;


    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.cost = (float) (product.getCost()/100.0);
        this.available = product.getAvailable();
    }
}
