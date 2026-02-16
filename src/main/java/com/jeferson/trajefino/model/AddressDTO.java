package com.jeferson.trajefino.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 3, max = 200, message = "Rua deve ter entre 3 e 200 caracteres")
    private String street;

    @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
    private String number;

    @Size(max = 100, message = "Complemento deve ter no máximo 100 caracteres")
    private String complement;

    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    private String neighborhood;

    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    private String city;

    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
    private String state;

    @Size(min = 8, max = 20, message = "CEP deve ter entre 8 e 20 caracteres")
    private String zipCode;

    @Size(max = 100, message = "País deve ter no máximo 100 caracteres")
    private String country;

    @Size(max = 50, message = "Tipo de endereço deve ter no máximo 50 caracteres")
    private String addressType; // HOME, WORK, BILLING, SHIPPING

    private Boolean isDefault;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
}
