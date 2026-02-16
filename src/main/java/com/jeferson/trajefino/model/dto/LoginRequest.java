package com.jeferson.trajefino.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username é obrigatório")
    private String userName;

    @NotBlank(message = "Password é obrigatória")
    private String password;
}
