package com.example.jpashop.repository.order.simple;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;

public record OrderSimpleV4DTO(
    Long orderId,
    String name,
    LocalDateTime orderDate,
    OrderStatus orderStatus,
    Address address
) {
}
