package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.BookForm;
import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping("/items/new")
    public String newItem(Model model) {

        model.addAttribute("form", new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String saveItem( @Valid BookForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "items/createItemForm";
        }

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        //book.setId(new Id(form.getPrice(),form.getStockQuantity(),form.getAuthor(),form.getIsbn()));
        itemService.saveItem(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "/items/itemList";
    }

    @GetMapping(value = "/items/{id}/edit")
    public String updateItem(@PathVariable Long id, Model model) {
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);

        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{id}/edit")
    public String updateItem(@PathVariable("id") Long id, BookForm form) {

        Book item = (Book) itemService.findOne(id);

        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        item.setAuthor(form.getAuthor());
        item.setIsbn(form.getIsbn());
        itemService.saveItem(item);

        return "redirect:/items";
    }

    @GetMapping(value = "/items/{id}/delete")
    public String deleteItem(@PathVariable("id") Long id) {

        Item item = itemService.findOne(id);

        itemService.delete(item);

        return "redirect:/items";

    }


}
