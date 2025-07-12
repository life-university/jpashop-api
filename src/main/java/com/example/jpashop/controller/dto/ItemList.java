package com.example.jpashop.controller.dto;

public record ItemList(
    Long id,
    String name,
    int price,
    int stockQuantity
) {

}
