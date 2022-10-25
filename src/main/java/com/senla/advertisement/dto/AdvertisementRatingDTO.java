package com.senla.advertisement.dto;

import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.entities.AdvertisementRating;

public class AdvertisementRatingDTO {

    private int id;

    private short value;

    private Advertisement advertisement;

    public AdvertisementRatingDTO(AdvertisementRating advertisementRating) {
        this.id = advertisementRating.getId();
        this.value = advertisementRating.getValue();
        this.advertisement = advertisementRating.getAdvertisement();
    }
}
