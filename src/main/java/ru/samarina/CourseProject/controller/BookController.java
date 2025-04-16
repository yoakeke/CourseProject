package ru.samarina.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.dto.BookStoreDto;
import ru.samarina.CourseProject.service.BookService;
import ru.samarina.CourseProject.service.StoreService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;
    private final StoreService storeService;

    @Autowired
    public BookController(BookService bookService, StoreService storeService) {
        this.bookService = bookService;
        this.storeService = storeService;
    }

    @GetMapping("/list-book")
    public String getBookListPage(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("stores", storeService.getAllStores());
        return "list-book";
    }

    @PostMapping("/books/add")
    public String addBook(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam Double price,
                          @RequestParam Long storeId) {

        BookDto bookDto = new BookDto();
        bookDto.setTitle(title);
        bookDto.setAuthor(author);
        bookDto.setStores(List.of(new BookStoreDto(storeId, price, 1)));

        bookService.addBook(bookDto);
        return "redirect:/list-book";
    }

}
