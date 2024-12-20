package ru.samarina.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.samarina.CourseProject.service.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    @GetMapping("/list-book")
    public String getBookListPage(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "list-book";
    }

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
}
