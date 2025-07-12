package com.example.jpashop.controller;

import com.example.jpashop.controller.dto.BookForm;
import com.example.jpashop.controller.dto.ItemList;
import com.example.jpashop.domain.item.Book;
import com.example.jpashop.service.ItemService;
import com.example.jpashop.service.dto.ItemUpdateDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", BookForm.empty());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String createForm(BookForm form) {

        Book book = Book.builder()
            .name(form.name())
            .price(form.price())
            .stockQuantity(form.stockQuantity())
            .author(form.author())
            .isbn(form.isbn())
            .build();

        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<ItemList> items = itemService.findItems().stream().map(
            item -> new ItemList(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStockQuantity()
            )
        ).toList();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Book findItem = (Book) itemService.findById(itemId);

        BookForm form = new BookForm(
            findItem.getId(),
            findItem.getName(),
            findItem.getPrice(),
            findItem.getStockQuantity(),
            findItem.getAuthor(),
            findItem.getIsbn()
        );
        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {

//        Book book = Book.builder()
//            .id(itemId)
//            .name(form.name())
//            .price(form.price())
//            .stockQuantity(form.stockQuantity())
//            .author(form.author())
//            .isbn(form.isbn())
//            .build();

//        itemService.saveItem(book);
//        itemService.updateItem(book);
//        itemService.updateItem2(itemId, form.name(), form.price(), form.stockQuantity());

        ItemUpdateDTO itemUpdateDTO = new ItemUpdateDTO(itemId, form.name(), form.price(), form.stockQuantity());
        itemService.updateItem3(itemUpdateDTO);

        return "redirect:/items";
    }
}
