package com.example.jpashop.api;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderItem;
import com.example.jpashop.domain.OrderStatus;
import com.example.jpashop.query_service.OrderQueryService;
import com.example.jpashop.repository.OrderRepository;
import com.example.jpashop.repository.order.query.OrderFlatDTO;
import com.example.jpashop.repository.order.query.OrderItemQueryDTO;
import com.example.jpashop.repository.order.query.OrderQueryDTO;
import com.example.jpashop.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderQueryService orderQueryService;

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

    @GetMapping("/api/v5/orders")
    public Result ordersV5() {
        List<OrderQueryDTO> orders = orderQueryRepository.findOrderQueryDTO();
        return new Result<>(orders.size(), orders);
    }

    @GetMapping("/api/v6/orders")
    public Result ordersV6() {
        List<OrderQueryDTO> orders = orderQueryRepository.findOrderQueryDTOOptimization();
        return new Result<>(orders.size(), orders);
    }

    @GetMapping("/api/v7/orders")
    public Result ordersV7() {
        List<OrderFlatDTO> flatResult = orderQueryRepository.findOrderQueryDTOFlat();

        List<OrderQueryDTO> result = flatResult.stream()
            .collect(groupingBy(o ->
                        new OrderQueryDTO(o.getOrderId(), o.getName(), o.getOrderDate(), o.getStatus(), o.getAddress()),
                    mapping(o -> new OrderItemQueryDTO(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )
            ).entrySet().stream()
            .map(e -> new OrderQueryDTO(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getStatus(), e.getKey().getAddress(), e.getValue()))
            .toList();
        return new Result<>(result.size(), result);
    }

    @GetMapping("/api/v8/orders")
    public Result<List<com.example.jpashop.query_service.OrderQueryDTO>> ordersV8() {
        return orderQueryService.ordersV8();
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
