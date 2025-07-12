package com.example.jpashop.service.dto;

public record ItemUpdateDTO(
    Long itemId,
    String name,
    int price,
    int stockQuantity
) {

}
