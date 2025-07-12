package com.example.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDTO> findOrderQueryDTO() {
        List<OrderQueryDTO> result = findOrders();
        result.forEach(o -> {
            o.setOrderItems(findOrderItems(o.getOrderId()));
        });
        return result;
    }

    public List<OrderQueryDTO> findOrderQueryDTOOptimization() {
        List<OrderQueryDTO> result = findOrders();
        List<Long> orderIds = result.stream().map(OrderQueryDTO::getOrderId).toList();

        List<OrderItemQueryDTO> orderItems = em.createQuery("select new com.example.jpashop.repository.order.query.OrderItemQueryDTO(" +
                "oi.order.id, i.name, oi.orderPrice, oi.count" +
                ") from OrderItem oi join oi.item i" +
                " where oi.order.id IN :orderIds", OrderItemQueryDTO.class)
            .setParameter("orderIds", orderIds)
            .getResultList();
        Map<Long, List<OrderItemQueryDTO>> orderItemMap = orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDTO::orderId));

        result.forEach(o -> {
            o.setOrderItems(orderItemMap.get(o.getOrderId()));
        });
        return result;
    }

    public List<OrderQueryDTO> findOrders() {
        return em.createQuery("select new com.example.jpashop.repository.order.query.OrderQueryDTO(" +
                "o.id, m.name, o.orderDate, o.status, d.address" +
                ") from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderQueryDTO.class)
            .getResultList();
    }

    public List<OrderItemQueryDTO> findOrderItems(Long orderId) {
        return em.createQuery("select new com.example.jpashop.repository.order.query.OrderItemQueryDTO(" +
                "oi.order.id, i.name, oi.orderPrice, oi.count" +
                ") from OrderItem oi join oi.item i" +
                " where oi.order.id = :orderId", OrderItemQueryDTO.class)
            .setParameter("orderId", orderId)
            .getResultList();
    }

    public List<OrderFlatDTO> findOrderQueryDTOFlat() {
        return em.createQuery("select new com.example.jpashop.repository.order.query.OrderFlatDTO(" +
                    "o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count" +
                    ")" +
                    " from Order o" +
                    " join o.member m" +
                    " join o.delivery d" +
                    " join o.orderItems oi" +
                    " join oi.item i"
            , OrderFlatDTO.class)
            .getResultList();
    }

}
