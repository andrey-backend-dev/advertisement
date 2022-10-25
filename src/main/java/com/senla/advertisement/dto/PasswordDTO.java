package com.senla.advertisement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordDTO {

    private String oldPassword;

    private String password;
}
