package com.shopkart.dto;

import com.shopkart.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Long productId;
    private String title;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal lineTotal;

    public static CartItemResponse from(CartItem c) {
        BigDecimal lineTotal = c.getProduct().getPrice().multiply(BigDecimal.valueOf(c.getQuantity()));

        return CartItemResponse.builder()
                .id(c.getId())
                .productId(c.getProduct().getId())
                .title(c.getProduct().getTitle())
                .imageUrl(c.getProduct().getImageUrl())
                .price(c.getProduct().getPrice())
                .quantity(c.getQuantity())
                .lineTotal(lineTotal)
                .build();
    }
}
