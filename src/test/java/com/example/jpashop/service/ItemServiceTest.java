package com.example.jpashop.service;

import com.example.jpashop.domain.item.Book;
import com.example.jpashop.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Test
    void saveItem() {
        // given
        Book book = Book.builder()
            .name("Test Item")
            .price(10000)
            .stockQuantity(10)
            .author("Author Name")
            .isbn("123-4567890123")
            .build();

        // when
        itemService.saveItem(book);

        // then
        Item foundBook = itemService.findById(book.getId());
        Assertions.assertThat(foundBook.getName()).isEqualTo(book.getName());
        Assertions.assertThat(foundBook.getPrice()).isEqualTo(book.getPrice());
        Assertions.assertThat(foundBook.getStockQuantity()).isEqualTo(book.getStockQuantity());
    }

    @Test
    void changeStock() {
        // given
        Book book = Book.builder()
            .name("Test Item")
            .price(10000)
            .stockQuantity(10)
            .author("Author Name")
            .isbn("123-4567890123")
            .build();

        // when
        itemService.saveItem(book);

        // then
        Item foundBook = itemService.findById(book.getId());
        foundBook.addStock(1);
        Assertions.assertThat(foundBook.getStockQuantity()).isEqualTo(11);
        foundBook.removeStock(10);
        Assertions.assertThat(foundBook.getStockQuantity()).isEqualTo(1);
    }
}