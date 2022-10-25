package com.senla.advertisement.dto;

import com.senla.advertisement.entities.User;
import com.senla.advertisement.utils.enums.Status;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class UserForUnknownsDTO {

    private Integer id;

    private String username;

    private Status status;

    private String name;

    private String phone;

    private String email;

    private LocalDateTime registeredSince;

    private String about;

    public UserForUnknownsDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.status = user.getStatus();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.registeredSince = user.getRegisteredSince();
        this.about = user.getAbout();
    }
}
