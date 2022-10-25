package com.senla.advertisement.dto;

import com.senla.advertisement.entities.Advertisement;
import com.senla.advertisement.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementResponseDTO {

    private Integer id;

    private String name;

    private Status status;

    private LocalDateTime createdAt;

    private String about;

    private Float rating;

    private String user;


    public AdvertisementResponseDTO(Advertisement advertisement) {
        this.id = advertisement.getId();
        this.name = advertisement.getName();
        this.status = advertisement.getStatus();
        this.createdAt = advertisement.getCreated();
        this.about = advertisement.getAbout();
        this.rating = (float) (advertisement.getRating().getValue()/100.0);
        this.user = advertisement.getUser().getUsername();
    }
}
