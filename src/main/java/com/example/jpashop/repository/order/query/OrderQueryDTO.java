package com.example.jpashop.repository.order.query;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDTO {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;
    private List<OrderItemQueryDTO> orderItems;

    public OrderQueryDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
    }

    public OrderQueryDTO(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address, List<OrderItemQueryDTO> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
        this.orderItems = orderItems;
    }
}
