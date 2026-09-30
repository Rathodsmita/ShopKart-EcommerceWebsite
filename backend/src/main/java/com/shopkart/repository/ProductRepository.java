package com.shopkart.repository;

import com.shopkart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByCategory_SlugAndActiveTrue(String slug);
    List<Product> findByTitleContainingIgnoreCaseAndActiveTrue(String title);
}
