package com.senla.advertisement.services;

import com.senla.advertisement.dao.AdvertisementRatingDAO;
import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.entities.AdvertisementRating;
import com.senla.advertisement.services.interfaces.IAdvertisementRatingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AdvertisementRatingService implements IAdvertisementRatingService {

    @Autowired
    private AdvertisementRatingDAO advertisementRatingDAO;


    @Override
    public AdvertisementRating findById(int id) {
        return advertisementRatingDAO.findById(id).orElse(null);
    }


    @Override
    public AdvertisementRating create(Advertisement advertisement) {
        AdvertisementRating advertisementRating = new AdvertisementRating();
        advertisementRating.setValue((short) 0);
        advertisementRating.setPromotedValue((short) 0);
        advertisementRating.setAdvertisement(advertisement);
        return advertisementRatingDAO.save(advertisementRating);
    }

    @Override
    public void correctAdvertisementPromotions() {
        advertisementRatingDAO.correctAdvertisementPromotions(LocalDateTime.now());
    }
}
