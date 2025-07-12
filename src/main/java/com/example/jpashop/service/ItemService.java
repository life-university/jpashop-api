package com.example.jpashop.service;

import com.example.jpashop.domain.item.Item;
import com.example.jpashop.repository.ItemRepository;
import com.example.jpashop.service.dto.ItemUpdateDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Transactional
    public void updateItem(Item updatedItem) {
        Item currentItem = itemRepository.findById(updatedItem.getId());
//        currentItem.setName(updatedItem.getName());
//        currentItem.setPrice(updatedItem.getPrice());
//        currentItem.setStockQuantity(updatedItem.getStockQuantity());
        currentItem.editFromController(updatedItem.getName(), updatedItem.getPrice(), updatedItem.getStockQuantity());
    }

    @Transactional
    public void updateItem2(Long itemId, String name, int price, int stockQuantity) {
        Item currentItem = itemRepository.findById(itemId);
        currentItem.editFromController(name, price, stockQuantity);
    }

    @Transactional
    public void updateItem3(ItemUpdateDTO dto) {
        Item currentItem = itemRepository.findById(dto.itemId());
        currentItem.editFromController(dto.name(), dto.price(), dto.stockQuantity());
    }
}
