package com.example.jpashop.query_service;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderQueryDTO(
    Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address, List<OrderItemQueryDTO> orderItems
) {
    public static OrderQueryDTO of(Order order) {
        order.getOrderItems().forEach(o -> o.getItem().getName());
        return new OrderQueryDTO(
            order.getId(),
            order.getMember().getName(),
            order.getOrderDate(),
            order.getStatus(),
            order.getDelivery().getAddress(),
            order.getOrderItems().stream().map(OrderItemQueryDTO::of).toList()
        );
    }
}