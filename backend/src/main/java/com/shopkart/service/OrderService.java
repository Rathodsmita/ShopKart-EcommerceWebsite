package com.shopkart.service;

import com.shopkart.dto.OrderRequest;
import com.shopkart.dto.OrderResponse;
import com.shopkart.exception.BadRequestException;
import com.shopkart.exception.ResourceNotFoundException;
import com.shopkart.model.CartItem;
import com.shopkart.model.Order;
import com.shopkart.model.OrderItem;
import com.shopkart.model.OrderStatus;
import com.shopkart.model.User;
import com.shopkart.repository.CartItemRepository;
import com.shopkart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public OrderResponse placeOrder(User user, OrderRequest request) {
        List<CartItem> cartItems = cartItemRepository.findByUser_Id(user.getId());

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Your cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .shippingAddress(request.getShippingAddress())
                .contactMobile(request.getContactMobile())
                .status(OrderStatus.PLACED)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            BigDecimal price = cartItem.getProduct().getPrice();
            int qty = cartItem.getQuantity();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .productTitle(cartItem.getProduct().getTitle())
                    .productImage(cartItem.getProduct().getImageUrl())
                    .priceAtPurchase(price)
                    .quantity(qty)
                    .build();

            order.getItems().add(orderItem);
            total = total.add(price.multiply(BigDecimal.valueOf(qty)));
        }

        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);

        // Checkout clears the cart, same as most e-commerce flows.
        cartItemRepository.deleteByUser_Id(user.getId());

        return OrderResponse.from(saved);
    }

    public List<OrderResponse> getMyOrders(User user) {
        return orderRepository.findByUser_IdOrderByPlacedAtDesc(user.getId()).stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse getMyOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Order not found: " + orderId);
        }

        return OrderResponse.from(order);
    }

    // ---- admin ----

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional
    public OrderResponse updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        order.setStatus(status);
        return OrderResponse.from(orderRepository.save(order));
    }
}
