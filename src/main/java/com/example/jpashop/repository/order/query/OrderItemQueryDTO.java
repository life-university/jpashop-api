package com.example.jpashop.repository.order.query;

public record OrderItemQueryDTO(
    Long orderId, String itemName, int orderPrice, int count
) {
}
