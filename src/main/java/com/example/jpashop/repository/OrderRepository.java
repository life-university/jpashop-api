package com.example.jpashop.repository;

import com.example.jpashop.domain.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
//@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

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

    public List<Order> findAllBySearch(OrderSearch orderSearch) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return query
            .select(order)
            .from(order)
            .join(order.member, member)
            .where(
                orderStatusEq(orderSearch.getOrderStatus()),
                memberNameLike(orderSearch.getMemberName())
            )
            .limit(1000)
            .fetch();
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return QOrder.order.status.eq(orderStatus);
    }

    private BooleanExpression memberNameLike(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return QMember.member.name.like(name);
    }

}
