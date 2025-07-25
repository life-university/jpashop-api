package com.example.jpashop.repository;

import com.example.jpashop.domain.item.Item;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item); // merge는 기존 엔티티를 찾아서 업데이트하는 방식
        }
    }

    public Item findById(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em
            .createQuery("select i from Item i", Item.class)
            .getResultList();
    }
}
