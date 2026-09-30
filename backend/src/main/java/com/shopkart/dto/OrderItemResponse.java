package com.shopkart.dto;

import com.shopkart.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long productId;
    private String title;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;

    public static OrderItemResponse from(OrderItem i) {
        return OrderItemResponse.builder()
                .productId(i.getProduct() != null ? i.getProduct().getId() : null)
                .title(i.getProductTitle())
                .imageUrl(i.getProductImage())
                .price(i.getPriceAtPurchase())
                .quantity(i.getQuantity())
                .build();
    }
}
