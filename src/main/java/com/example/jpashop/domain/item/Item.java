package com.example.jpashop.domain.item;

import com.example.jpashop.domain.Category;
import com.example.jpashop.exception.NotEnoughStockException;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@SuperBuilder
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // buiness logic
    // 재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int resultQuantity = this.stockQuantity - quantity;
        if (resultQuantity < 0) {
            throw new NotEnoughStockException("Need more quantity. Current stock: " + this.stockQuantity + ", require stock: " + quantity);
        }
        this.stockQuantity = resultQuantity;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void editFromController(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
