package com.example.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Album")
@Getter
@SuperBuilder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Album extends Item {

    private String artist;
    private String etc;
}
