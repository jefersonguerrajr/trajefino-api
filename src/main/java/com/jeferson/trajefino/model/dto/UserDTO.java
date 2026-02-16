package com.jeferson.trajefino.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeferson.trajefino.model.enums.UserRole;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    private String userName;

    private String name;

    private String fullName;

    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String password;

    private String birthDate;

    private UserRole role; // Opcional, padrão será ROLE_CUSTOMER
}
