package com.example.jpashop.controller.dto;

import com.example.jpashop.domain.Address;

public record MemberList(
    Long id,
    String name,
    Address address
) {

}
