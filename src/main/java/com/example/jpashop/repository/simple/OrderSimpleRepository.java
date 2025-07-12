package com.example.jpashop.repository.simple;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleRepository {

    private final EntityManager em;


    public List<SimpleOrderV4DTO> findAll_DTO() {
        return em.createQuery("select new com.example.jpashop.repository.simple.SimpleOrderV4DTO(" +
            "o.id, m.name, o.orderDate, o.status, d.address)  from Order o" +
            " join o.member m" +
            " join o.delivery d", SimpleOrderV4DTO.class).getResultList();
    }

}
