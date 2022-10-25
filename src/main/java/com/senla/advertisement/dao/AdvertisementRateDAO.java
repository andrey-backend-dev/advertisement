package com.senla.advertisement.dao;

import com.senla.advertisement.entities.AdvertisementRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdvertisementRateDAO extends JpaRepository<AdvertisementRate, Integer> {

    @Query(value = "select * from advertisementrate ar where ar.user_id = :user_id && ar.ad_id = :ad_id", nativeQuery = true)
    AdvertisementRate findByUserIdAndAdId(@Param("user_id") int userId, @Param("ad_id") int adId);

    @Query(value = "select sum(value)/count(*) from advertisementrate where ad_id = :ad_id", nativeQuery = true)
    Float calculateAdvertisementRating(@Param("ad_id") int adId);
}
