package com.example.jpashop.repository;

import com.example.jpashop.domain.OrderItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final EntityManager em;

    public void save(OrderItem orderItem) {
        em.persist(orderItem);
    }

    public OrderItem findById(Long id) {
        return em.find(OrderItem.class, id);
    }

}
