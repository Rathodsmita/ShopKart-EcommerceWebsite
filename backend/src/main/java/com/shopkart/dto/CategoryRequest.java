package com.shopkart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Slug is required")
    private String slug;

    @NotBlank(message = "Label is required")
    private String label;

    private String imageUrl;
}
