package com.shopkart.service;

import com.shopkart.dto.CartItemRequest;
import com.shopkart.dto.CartItemResponse;
import com.shopkart.dto.CartResponse;
import com.shopkart.exception.ResourceNotFoundException;
import com.shopkart.model.CartItem;
import com.shopkart.model.Product;
import com.shopkart.model.User;
import com.shopkart.repository.CartItemRepository;
import com.shopkart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartResponse getCart(User user) {
        List<CartItem> items = cartItemRepository.findByUser_Id(user.getId());
        return toCartResponse(items);
    }

    @Transactional
    public CartResponse addOrUpdateItem(User user, CartItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + request.getProductId()));

        CartItem item = cartItemRepository.findByUser_IdAndProduct_Id(user.getId(), product.getId())
                .orElse(CartItem.builder().user(user).product(product).quantity(0).build());

        item.setQuantity(item.getQuantity() == null ? request.getQuantity() : item.getQuantity() + request.getQuantity());
        cartItemRepository.save(item);

        return getCart(user);
    }

    @Transactional
    public CartResponse updateQuantity(User user, Long productId, Integer quantity) {
        CartItem item = cartItemRepository.findByUser_IdAndProduct_Id(user.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not in cart"));

        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        return getCart(user);
    }

    @Transactional
    public CartResponse removeItem(User user, Long productId) {
        cartItemRepository.deleteByUser_IdAndProduct_Id(user.getId(), productId);
        return getCart(user);
    }

    @Transactional
    public void clearCart(User user) {
        cartItemRepository.deleteByUser_Id(user.getId());
    }

    private CartResponse toCartResponse(List<CartItem> items) {
        List<CartItemResponse> responses = items.stream().map(CartItemResponse::from).toList();

        int totalItems = responses.stream().mapToInt(CartItemResponse::getQuantity).sum();
        BigDecimal subtotal = responses.stream()
                .map(CartItemResponse::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .items(responses)
                .totalItems(totalItems)
                .subtotal(subtotal)
                .build();
    }
}
