package com.example.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("BookTable")
@Getter
@SuperBuilder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;
    private String isbn;
}
