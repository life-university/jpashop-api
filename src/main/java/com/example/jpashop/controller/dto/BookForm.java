package com.example.jpashop.controller.dto;

public record BookForm(
    Long id,
    String name,
    int price,
    int stockQuantity,
    String author,
    String isbn
) {

    public static BookForm empty() {
        return new BookForm(null, "", 0, 0, "", "");
    }
}
