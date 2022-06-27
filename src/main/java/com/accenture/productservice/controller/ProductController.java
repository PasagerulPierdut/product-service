package com.accenture.productservice.controller;

import com.accenture.productservice.model.dto.ProductDto;
import com.accenture.productservice.model.dto.SearchCriteria;
import com.accenture.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDto productDto) {
        LOGGER.log(Level.FINE, "Trying to add new product, with code: {0}",  productDto.getCode());
        return new ResponseEntity<>(productService.create(productDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll(SearchCriteria searchCriteria) {

        return new ResponseEntity<>(productService.findAll(searchCriteria), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductDto> findOne(@PathVariable String code) {
        return new ResponseEntity<>(productService.findOne(code), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String>updateProduct(@Valid @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.update(productDto), HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String>deleteProduct(@PathVariable String code) {
        productService.delete(code);
return new ResponseEntity<>("Product with code: " + code + " was deleted.", HttpStatus.GONE);
    }
}
