package com.example.jpashop.repository.simple;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.OrderStatus;

import java.time.LocalDateTime;

public record SimpleOrderV4DTO(
    Long orderId,
    String name,
    LocalDateTime orderDate,
    OrderStatus orderStatus,
    Address address
) {
}
