package com.example.jpashop.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record MemberForm(
    @NotEmpty(message = "Member name is required")
    String name,
    String city,
    String street,
    String zipcode
) {

    public static MemberForm empty() {
        return new MemberForm("", "", "", "");
    }
}
