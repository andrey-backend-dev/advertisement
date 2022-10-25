package com.senla.advertisement.services.interfaces;

import com.senla.advertisement.dto.AdvertisementRateRequestDTO;
import com.senla.advertisement.dto.AdvertisementRateResponseDTO;

import java.util.List;

public interface IAdvertisementRateService {
    List<AdvertisementRateResponseDTO> getList();
    AdvertisementRateResponseDTO create(AdvertisementRateRequestDTO advertisementRateDTO, String token);
    boolean deleteById(int id, String token);
}
