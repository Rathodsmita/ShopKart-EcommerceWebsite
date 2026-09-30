package com.shopkart.dto;

import com.shopkart.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private String category;
    private String categoryLabel;
    private BigDecimal price;
    private BigDecimal mrp;
    private Integer discountPercent;
    private Double rating;
    private Integer ratingCount;
    private String imageUrl;
    private String badge;
    private Integer stock;

    public static ProductResponse from(Product p) {
        int discount = 0;
        if (p.getMrp() != null && p.getMrp().compareTo(BigDecimal.ZERO) > 0 && p.getPrice() != null) {
            discount = p.getMrp().subtract(p.getPrice())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(p.getMrp(), 0, java.math.RoundingMode.HALF_UP)
                    .intValue();
        }

        return ProductResponse.builder()
                .id(p.getId())
                .title(p.getTitle())
                .category(p.getCategory() != null ? p.getCategory().getSlug() : null)
                .categoryLabel(p.getCategory() != null ? p.getCategory().getLabel() : null)
                .price(p.getPrice())
                .mrp(p.getMrp())
                .discountPercent(discount)
                .rating(p.getRating())
                .ratingCount(p.getRatingCount())
                .imageUrl(p.getImageUrl())
                .badge(p.getBadge())
                .stock(p.getStock())
                .build();
    }
}
