package com.shopkart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Category slug is required")
    private String categorySlug;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "MRP is required")
    @Positive(message = "MRP must be greater than 0")
    private BigDecimal mrp;

    private String imageUrl;
    private String badge;
    private Integer stock;
}
