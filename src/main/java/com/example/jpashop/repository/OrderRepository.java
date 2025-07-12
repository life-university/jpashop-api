package com.example.jpashop.repository;

import com.example.jpashop.domain.Order;
import com.example.jpashop.repository.simple.SimpleOrderV4DTO;
import jakarta.persistence.EntityManager;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
            .getResultList();
    }

    public List<Order> findAll_MemberDelivery() {
        return em.createQuery("select o from Order o" +
            " join fetch o.member m" +
            " join fetch o.delivery d", Order.class).getResultList();
    }

    public List<Order> findAll_OrderItem_Item() {
        return em.createQuery("select o from Order o" +
            " join fetch o.member m" +
            " join fetch o.delivery d" +
            " join fetch o.orderItems oi" +
            " join fetch oi.item i", Order.class).getResultList();
    }
}
