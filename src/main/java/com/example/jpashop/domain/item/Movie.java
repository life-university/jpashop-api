package com.example.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Movie")
@Getter
@SuperBuilder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Movie extends Item {

    private String director;
    private String actor;
}
