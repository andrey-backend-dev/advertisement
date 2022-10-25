package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.entities.AdvertisementRating;

public interface IAdvertisementRatingService {

    AdvertisementRating findById(int id);
    AdvertisementRating create(Advertisement advertisement);
    void correctAdvertisementPromotions();
}
