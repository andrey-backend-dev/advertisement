package com.senla.advertisement.dao;

import com.senla.advertisement.entities.AdvertisementRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AdvertisementRatingDAO extends JpaRepository<AdvertisementRating, Integer> {
    @Modifying
    @Query(value = "update advertisementrating ar set ar.promoted_value = ar.value where ar.payment_period < :date", nativeQuery = true)
    void correctAdvertisementPromotions(@Param("date") LocalDateTime dateTime);
}
