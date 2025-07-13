package com.example.jpashop.query_service;

import com.example.jpashop.api.Result;
import com.example.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public Result<List<OrderQueryDTO>> ordersV8() {
        List<OrderQueryDTO> all = orderRepository.findAll().stream().map(OrderQueryDTO::of).toList();
        return new Result<>(all.size(), all);
    }
}
