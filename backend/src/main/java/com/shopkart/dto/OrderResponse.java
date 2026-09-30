package com.shopkart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopkart.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String status;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String contactMobile;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime placedAt;

    private List<OrderItemResponse> items;

    public static OrderResponse from(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .status(o.getStatus().name())
                .totalAmount(o.getTotalAmount())
                .shippingAddress(o.getShippingAddress())
                .contactMobile(o.getContactMobile())
                .placedAt(o.getPlacedAt())
                .items(o.getItems().stream().map(OrderItemResponse::from).toList())
                .build();
    }
}
