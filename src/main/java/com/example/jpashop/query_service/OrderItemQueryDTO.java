package com.example.jpashop.query_service;

import com.example.jpashop.domain.OrderItem;

public record OrderItemQueryDTO(
    Long orderItemId, int orderPrice, int count, String itemName
) {
    public static OrderItemQueryDTO of(OrderItem orderItem) {
        return new OrderItemQueryDTO(
            orderItem.getId(),
            orderItem.getOrderPrice(),
            orderItem.getCount(),
            orderItem.getItem().getName()
        );
    }
}