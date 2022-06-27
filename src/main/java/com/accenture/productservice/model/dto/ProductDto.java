package com.accenture.productservice.model.dto;

import com.accenture.productservice.model.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {

    @NotEmpty(message = "Product code must be provided.")
    private String code;

    @NotEmpty(message = "The product must have a name.")
    private String name;

    @NotNull
    private ProductCategory category;

    private String description;

    @Min(value = 1, message = "A minimum value of 1 must be provided.")
    private int unitsPerSet;

    @Positive(message = "The price must have a positive value.")
    private BigDecimal price;
}
