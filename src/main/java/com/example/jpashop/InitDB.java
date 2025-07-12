package com.example.jpashop;

import com.example.jpashop.domain.*;
import com.example.jpashop.domain.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        Member member1 = initService.createMember("member1", "city1", "street1", "zip1");
        Member member2 = initService.createMember("member2", "city2", "street2", "zip2");
        Book book1 = initService.createBook("Book1", 10000, 100);
        Book book2 = initService.createBook("Book2", 20000, 100);
        Delivery delivery1 = initService.createDelivery(member1.getAddress());
        Delivery delivery2 = initService.createDelivery(member1.getAddress());
        initService.order(member1, delivery1, book1, book2);
        initService.order(member2, delivery2, book1, book2);
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public Member createMember(String name, String city, String street, String zip) {
            Member member = Member.builder().name(name)
                .address(new Address(city, street, zip))
                .build();
            em.persist(member);
            return member;
        }

        public Book createBook(String name, int price, int stockQuantity) {
            Book book = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
            em.persist(book);
            return book;
        }

        public Delivery createDelivery(Address address) {
            Delivery delivery = Delivery.builder().address(address).build();
            em.persist(delivery);
            return delivery;
        }

        public void order(Member member, Delivery delivery, Book book1, Book book2) {
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order1 = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order1);
            orderItem1.setOrder(order1);
            orderItem2.setOrder(order1);
            em.persist(orderItem1);
            em.persist(orderItem2);
        }
    }
}
