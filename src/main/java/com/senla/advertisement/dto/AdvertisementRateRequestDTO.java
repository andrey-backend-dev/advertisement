package com.senla.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementRateRequestDTO {

    private short value;

    private int adId;
}
