package com.accenture.productservice.utils.conversion;

import com.accenture.productservice.model.Product;
import com.accenture.productservice.model.dto.ProductDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .code(product.getCode())
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .unitsPerSet(product.getUnitsPerSet())
                .price(product.getPrice())
        .build();
    }

    public Product toEntity(ProductDto productDto) {
        return Product.builder()
                .code(productDto.getCode())
                .name(productDto.getName())
                .category(productDto.getCategory())
                .description(productDto.getDescription())
                .unitsPerSet(productDto.getUnitsPerSet())
                .price(productDto.getPrice())
                .build();
    }

    public Product update(ProductDto productDto, Product product) {
        BeanUtils.copyProperties(productDto, product, "code");
        return product;
    }
}
