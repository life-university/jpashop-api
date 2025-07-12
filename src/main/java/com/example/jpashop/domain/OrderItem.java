package com.example.jpashop.domain;

import com.example.jpashop.domain.item.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    public void setOrder(Order order) {
        this.order = order;
    }

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = OrderItem.builder()
            .item(item)
            .orderPrice(orderPrice)
            .count(count)
            .build();

        // 재고 수정
        item.removeStock(count);
        return orderItem;
    }

    // 비즈니스 로직
    public void cancel() {
        // 취소한 주문의 상품들의 재고 수량 수정
        item.addStock(count);
    }

    public int getTotalPrice() {
        return count * orderPrice;
    }
}
