package com.accenture.productservice.model.dto;

import com.accenture.productservice.model.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private String code;
    private String name;
    private ProductCategory category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer pageSize;
    private String sortBy;
}
