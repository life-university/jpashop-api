package com.example.jpashop.service;

import com.example.jpashop.domain.Delivery;
import com.example.jpashop.domain.Member;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderItem;
import com.example.jpashop.domain.OrderSearch;
import com.example.jpashop.domain.item.Item;
import com.example.jpashop.repository.DeliveryRepository;
import com.example.jpashop.repository.ItemRepository;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.repository.OrderItemRepository;
import com.example.jpashop.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // lookup entity
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        // create delivery
        Delivery delivery = Delivery.builder()
            .address(member.getAddress())
            .build();
        deliveryRepository.save(delivery);

        // create order item
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // create order
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);

        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId);
        order.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll();
    }
}
