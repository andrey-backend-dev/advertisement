package com.senla.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDTO {

    private int id;

    private String username;

    private String token;
}
