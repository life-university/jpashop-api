package com.example.jpashop.api;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderStatus;
import com.example.jpashop.repository.OrderRepository;
import com.example.jpashop.repository.order.simple.OrderSimpleRepository;
import com.example.jpashop.repository.order.simple.OrderSimpleV4DTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    // find Order
    // Order -> Member ( many to one relationship )
    // Order -> Delivery ( one to one relationship )

    private final OrderRepository orderRepository;
    private final OrderSimpleRepository orderSimpleRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll();
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<SimpleOrderDTO> all = orderRepository.findAll().stream().map(SimpleOrderDTO::of).toList();
        return new Result<>(all.size(), all);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<SimpleOrderDTO> all = orderRepository.findAll_MemberDelivery().stream().map(SimpleOrderDTO::of).toList();
        return new Result<>(all.size(), all);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4() {
        List<OrderSimpleV4DTO> all = orderSimpleRepository.findAll_DTO();
        return new Result<>(all.size(), all);
    }

    record SimpleOrderDTO(
        Long orderId,
        String name,
        LocalDateTime orderDate,
        OrderStatus orderStatus,
        Address address
    ) {
        public static SimpleOrderDTO of(Order order) {
            return new SimpleOrderDTO(
                order.getId(),
                order.getMember().getName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getDelivery().getAddress()
            );
        }
    }
}
