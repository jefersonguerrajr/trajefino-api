package com.jeferson.trajefino.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
    private String name;

    private String description;

    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal price;

    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer stock;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String category;

    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    private String brand;

    @Size(max = 50, message = "Código de barras deve ter no máximo 50 caracteres")
    private String barcode;

    private Boolean active;
}
