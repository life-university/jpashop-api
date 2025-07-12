package com.example.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import com.example.jpashop.domain.Order;
import com.example.jpashop.domain.OrderStatus;
import com.example.jpashop.domain.item.Book;
import com.example.jpashop.exception.NotEnoughStockException;
import com.example.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager em;

    private Member createMember() {
        Member member = Member.builder()
            .name("test user1")
            .address(new Address("서울", "강가", "123-123"))
            .build();
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
            .name(name)
            .price(price)
            .stockQuantity(stockQuantity)
            .build();
        em.persist(book);
        return book;
    }

    @Test
    void orderTest() {
        // given
        Member member = createMember();
        Book book = createBook("test book", 10000, 10);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order findOrder = orderRepository.findById(orderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getTotalPrice()).isEqualTo(book.getPrice() * orderCount);
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void orderStockQuantityOver() {
        // given
        Member member = createMember();
        Book book = createBook("test book", 10000, 10);
        int orderCount = 20;

        // when & then
        assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount))
            .isInstanceOf(NotEnoughStockException.class);

    }

    @Test
    void orderCancel() {
        // given
        Member member = createMember();
        Book book = createBook("test book", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order findOrder = orderRepository.findById(orderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(10).isEqualTo(book.getStockQuantity());
    }

}