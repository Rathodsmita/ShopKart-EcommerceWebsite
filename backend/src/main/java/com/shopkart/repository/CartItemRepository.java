package com.shopkart.repository;

import com.shopkart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser_Id(Long userId);
    Optional<CartItem> findByUser_IdAndProduct_Id(Long userId, Long productId);
    void deleteByUser_Id(Long userId);
    void deleteByUser_IdAndProduct_Id(Long userId, Long productId);
}
