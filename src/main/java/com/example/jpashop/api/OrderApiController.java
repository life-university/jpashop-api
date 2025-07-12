package com.example.jpashop.api;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderItem;
import com.example.jpashop.domain.OrderStatus;
import com.example.jpashop.repository.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll();
        return all;
    }

    @GetMapping("/api/v2/orders")
    public Result ordersV2() {
        List<OrderDTO> all = orderRepository.findAll().stream().map(OrderDTO::of).toList();
        return new Result<>(all.size(), all);
    }

    @GetMapping("/api/v3/orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAll_OrderItem_Item();
        List<OrderDTO> all = orders.stream().map(OrderDTO::of).toList();
        return new Result<>(all.size(), all);
    }

    @GetMapping("/api/v4/orders")
    public Result ordersV4Paging() {
        List<Order> orders = orderRepository.findAll_MemberDelivery();
        List<OrderDTO> all = orders.stream().map(OrderDTO::of).toList();
        return new Result<>(all.size(), all);
    }

    record OrderDTO(
        Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address, List<OrderItemDTO> orderItems
    ) {
        public static OrderDTO of(Order order) {
            order.getOrderItems().forEach(o -> o.getItem().getName());
            return new OrderDTO(
                order.getId(),
                order.getMember().getName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getDelivery().getAddress(),
                order.getOrderItems().stream().map(OrderItemDTO::of).toList()
            );
        }
    }

    record OrderItemDTO(
        Long orderItemId, int orderPrice, int count, String itemName
    ) {
        public static OrderItemDTO of(OrderItem orderItem) {
            return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getOrderPrice(),
                orderItem.getCount(),
                orderItem.getItem().getName()
            );
        }
    }
}
