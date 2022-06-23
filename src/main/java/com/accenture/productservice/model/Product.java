package com.accenture.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Enumerated
    @Column(name = "category")
    private ProductCategory category;
    @Column(name = "description")
    private String description;
    @Column(name = "boxing")
    private int unitsPerSet;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "created")
    private LocalDateTime createdAt;
    @Column(name = "modified")
    private LocalDateTime lastModifiedAt;
}
