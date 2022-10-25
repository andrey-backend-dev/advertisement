package com.senla.advertisement.dto;

import com.senla.advertisement.entities.AdvertisementRate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdvertisementRateResponseDTO {

    private Integer id;

    private String username;

    private String advertisement;

    private Short value;


    public AdvertisementRateResponseDTO(AdvertisementRate rate) {
        this.id = rate.getId();
        this.username = rate.getUser().getUsername();
        this.advertisement = rate.getAdvertisementRating().getAdvertisement().getName();
        this.value = (short) (rate.getValue()/100.0);
    }
}
