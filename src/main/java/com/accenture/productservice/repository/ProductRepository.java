package com.accenture.productservice.repository;

import com.accenture.productservice.model.Product;
import com.accenture.productservice.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByCode(String code);

    boolean existsByCode(String code);

    @Query(value = "SELECT * from products WHERE (:code IS NULL OR code = :code) " +
            "AND (:name IS NULL OR name LIKE CONCAT('%', :name, '%')) " +
            "AND (:category IS NULL OR category = :category) " +
            "AND (:minPrice IS NULL OR price > :minPrice) " +
            "AND (:maxPrice IS NULL OR price < :maxPrice) ", nativeQuery = true)
    Page<Product> findAll(
            @Param("code") String code,
            @Param("name") String name,
            @Param("category") ProductCategory category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);
}
