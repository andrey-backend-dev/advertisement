package com.senla.advertisement.dao;

import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.utils.enums.Status;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertisementDAO extends JpaRepository<Advertisement, Integer> {

    @Modifying
    @Query(value = "delete from advertisement a where a.id = :id", nativeQuery = true)
    void deleteAd(@Param("id") int id);

    List<Advertisement> findByStatus(Status status, Sort sort);

    List<Advertisement> findByNameLike(String nameLike);

    @Query(value = "select * from advertisement " +
            "join advertisementrating on advertisement.id = advertisementrating.id " +
            "where advertisement.status = 'ALIVE' " +
            "order by promoted_value desc", nativeQuery = true)
    List<Advertisement> findByRatingSortDesc(String orderName);
    @Query(value = "select * from advertisement " +
            "join advertisementrating on advertisement.id = advertisementrating.id " +
            "order by promoted_value desc", nativeQuery = true)
    List<Advertisement> findWholeListByRatingSortDesc(String orderName);
    @Query(value = "select * from advertisement " +
            "join advertisementrating on advertisement.id = advertisementrating.id " +
            "where advertisement.status = 'ALIVE' " +
            "order by promoted_value asc", nativeQuery = true)
    List<Advertisement> findByRatingSortAsc(String orderName);
    @Query(value = "select * from advertisement " +
            "join advertisementrating on advertisement.id = advertisementrating.id " +
            "order by promoted_value asc", nativeQuery = true)
    List<Advertisement> findWholeListByRatingSortAsc(String orderName);
}
