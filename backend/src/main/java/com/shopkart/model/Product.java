package com.shopkart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // MRP (maximum retail price) shown struck-through with a discount %
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal mrp;

    @Column(nullable = false)
    @Builder.Default
    private Double rating = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Integer ratingCount = 0;

    @Column(length = 500)
    private String imageUrl;

    // e.g. "Bestseller", "Deal", "Top Rated" — nullable, shown as a small tag
    @Column(length = 40)
    private String badge;

    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 100;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}
