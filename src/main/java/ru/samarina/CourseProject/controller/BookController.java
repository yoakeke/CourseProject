package ru.samarina.CourseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.service.BookService;
import ru.samarina.CourseProject.service.StoreService;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final StoreService storeService;

    public BookController(BookService bookService, StoreService storeService) {
        this.bookService = bookService;
        this.storeService = storeService;
    }

    @GetMapping("/list-book")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("stores", storeService.getAllStores());
        return "list-book";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam Long storeId,
                          @RequestParam Double price,
                          @RequestParam Integer quantity) {
        bookService.addBookWithStore(title, author, storeId, price, quantity);
        return "redirect:/books/list-book";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books/list-book";
    }

    @GetMapping("/selectStore")
    public String selectStore(@RequestParam Long bookId, @RequestParam Long storeId, Model model) {
        model.addAttribute("books", bookService.findAllBooksWithSelectedStore(bookId, storeId));
        model.addAttribute("stores", storeService.getAllStores());
        return "list-book";
    }
}