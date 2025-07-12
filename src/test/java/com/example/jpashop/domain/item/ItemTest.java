package com.example.jpashop.domain.item;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.example.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class ItemTest {

    @Test
    void removeStock() {
        // given
        Book book = Book.builder()
            .name("test book")
            .price(10000)
            .stockQuantity(10)
            .build();

        // when
        book.removeStock(2);

        // then
        assertEquals(8, book.getStockQuantity());
    }

    @Test
    void removeStockOver() {
        // given
        Book book = Book.builder()
            .name("test book")
            .price(10000)
            .stockQuantity(10)
            .build();

        // when & then
        assertThatThrownBy(() -> book.removeStock(11))
            .isInstanceOf(NotEnoughStockException.class);
    }
}