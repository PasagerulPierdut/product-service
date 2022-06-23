package com.accenture.productservice.service;

import com.accenture.productservice.exceptions.ExistingProductException;
import com.accenture.productservice.exceptions.ProductNotFoundException;
import com.accenture.productservice.model.Product;
import com.accenture.productservice.model.dto.ProductDto;
import com.accenture.productservice.model.dto.SearchCriteria;
import com.accenture.productservice.repository.ProductRepository;
import com.accenture.productservice.utils.conversion.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public String create(ProductDto productDto) {
        if (!productRepository.existsByCode(productDto.getCode())) {
            Product newProduct = mapper.toEntity(productDto);
            newProduct.setCreatedAt(LocalDateTime.now());
            productRepository.save(newProduct);
            return "Product with code: " + newProduct.getCode() + " saved successfully";
        } else {
            throw new ExistingProductException();
        }
    }

    public List<ProductDto> findAll(SearchCriteria searchCriteria) {

        if (searchCriteria.getPageSize() == null) {
            searchCriteria.setPageSize(20);
        }
        if(searchCriteria.getSortBy() == null) {
            searchCriteria.setSortBy("category");
        }
        Pageable p = PageRequest.of(0, searchCriteria.getPageSize(), Sort.Direction.ASC, searchCriteria.getSortBy());
        return productRepository.findAll(searchCriteria.getCode(), searchCriteria.getName(),
                        searchCriteria.getCategory(), searchCriteria.getMinPrice(), searchCriteria.getMaxPrice(), p)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ProductDto findOne(String code) {
        return mapper.toDto(productRepository.findProductByCode(code)
                .orElseThrow(ProductNotFoundException::new));
    }

    public String update(ProductDto productDto) {
        Product dbProduct = productRepository.findProductByCode(productDto.getCode())
                .orElseThrow(ProductNotFoundException::new);
        mapper.update(productDto, dbProduct);
        dbProduct.setLastModifiedAt(LocalDateTime.now());
        productRepository.save(dbProduct);
        return "Product with code " + dbProduct.getCode() + " updated successfully.";
    }

    public void delete(String code) {
        Product toBeDeleted = productRepository.findProductByCode(code)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(toBeDeleted);
    }
}