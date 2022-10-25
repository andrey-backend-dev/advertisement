package com.senla.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private int adId;

    private String name;

    private Integer cost;

    private Integer available;
}
