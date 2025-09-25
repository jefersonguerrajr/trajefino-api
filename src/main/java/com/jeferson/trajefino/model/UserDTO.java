package com.jeferson.trajefino.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "name obrigatório")
    private String name;

    @NotBlank(message = "userName obrigatório")
    private String userName;

    private String birthDate;
}
