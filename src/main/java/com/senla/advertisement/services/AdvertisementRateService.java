package com.senla.advertisement.services;

import com.senla.advertisement.dao.AdvertisementRateDAO;
import com.senla.advertisement.dao.AdvertisementRatingDAO;
import com.senla.advertisement.dao.UserDAO;
import com.senla.advertisement.dto.AdvertisementRateRequestDTO;
import com.senla.advertisement.dto.AdvertisementRateResponseDTO;
import com.senla.advertisement.entities.AdvertisementRate;
import com.senla.advertisement.entities.AdvertisementRating;
import com.senla.advertisement.entities.User;
import com.senla.advertisement.security.jwt.JwtProvider;
import com.senla.advertisement.services.interfaces.IAdvertisementRateService;
import com.senla.advertisement.services.interfaces.IAdvertisementRatingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdvertisementRateService implements IAdvertisementRateService {

    @Autowired
    private AdvertisementRateDAO advertisementRateDAO;
    @Autowired
    private IAdvertisementRatingService advertisementRatingService;
    @Autowired
    private AdvertisementRatingDAO advertisementRatingDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    @Transactional(readOnly = true)
    public List<AdvertisementRateResponseDTO> getList() {
        return advertisementRateDAO.findAll().stream().map(AdvertisementRateResponseDTO::new).toList();
    }

    @Override
    @Transactional
    public AdvertisementRateResponseDTO create(AdvertisementRateRequestDTO advertisementRateDTO, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        short value = advertisementRateDTO.getValue();
        if (value >= 1 && value <= 5) {
            value = (short) (value * 100);
            AdvertisementRate rate = advertisementRateDAO.findByUserIdAndAdId(user.getId(), advertisementRateDTO.getAdId());

            AdvertisementRateResponseDTO advertisementRateResponseDTO;

            if (rate == null) {
                rate = new AdvertisementRate();
                rate.setValue(value);
                rate.setAdvertisementRating(advertisementRatingDAO.findById(advertisementRateDTO.getAdId()).orElse(null));
                rate.setUser(user);
                advertisementRateResponseDTO = new AdvertisementRateResponseDTO(advertisementRateDAO.save(rate));
            } else {
                return null;
            }

            AdvertisementRating advertisementRating = advertisementRatingService.findById(advertisementRateDTO.getAdId());
            float arValue = advertisementRateDAO.calculateAdvertisementRating(advertisementRateDTO.getAdId());
            advertisementRating.setValue((short) arValue);
            if (advertisementRating.getPaymentPeriod() != null && advertisementRating.getPaymentPeriod().isAfter(LocalDateTime.now())) {
                advertisementRating.setPromotedValue((short) (arValue + 500));
            } else {
                advertisementRating.setPromotedValue((short) arValue);
            }

            return advertisementRateResponseDTO;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteById(int id, String token) {
        User user = userDAO.findByUsername(jwtProvider.getUsername(token));
        AdvertisementRate advertisementRate = advertisementRateDAO.findById(id).orElse(null);

        if (advertisementRate.getUser() == user) {
            AdvertisementRating advertisementRating = advertisementRate.getAdvertisementRating();
            advertisementRateDAO.deleteById(id);
            float arValue = advertisementRateDAO.calculateAdvertisementRating(advertisementRating.getId());
            advertisementRating.setValue((short) arValue);
            if (advertisementRating.getPaymentPeriod() != null && advertisementRating.getPaymentPeriod().isAfter(LocalDateTime.now())) {
                advertisementRating.setPromotedValue((short) (arValue + 500));
            } else {
                advertisementRating.setPromotedValue((short) arValue);
            }
            return true;
        }

        return false;
    }
}
