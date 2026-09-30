package com.shopkart.dto;

import com.shopkart.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String slug;
    private String label;
    private String imageUrl;

    public static CategoryResponse from(Category c) {
        return CategoryResponse.builder()
                .id(c.getId())
                .slug(c.getSlug())
                .label(c.getLabel())
                .imageUrl(c.getImageUrl())
                .build();
    }
}
