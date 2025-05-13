package ru.samarina.CourseProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.service.BookService;
import ru.samarina.CourseProject.service.StoreService;

import java.security.Principal;
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
    public String listBooks(Model model, Principal principal) {
        String userEmail = (principal != null) ? principal.getName() : null;

        List<BookDto> books = bookService.findAllBooks(userEmail);

        model.addAttribute("books", books);
        model.addAttribute("stores", storeService.getAllStores());

        // Добавляем пустой BookDto для формы добавления
        BookDto emptyBookDto = new BookDto();
        emptyBookDto.setInFavorite(false); // по умолчанию не в избранном
        model.addAttribute("bookDto", emptyBookDto);

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


    @PostMapping("books/delete")
    public String deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return "redirect:/books/list-book";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("stores", storeService.getAllStores());
        return "list-book";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute BookDto book,
                           @RequestParam Long storeId,
                           @RequestParam Double price,
                           @RequestParam Integer quantity) {
        bookService.updateBookWithStore(book, storeId, price, quantity);
        return "redirect:/books/list-book";
    }

    @GetMapping("/selectStore")
    public String selectStore(@RequestParam Long bookId, @RequestParam Long storeId, Model model) {
        model.addAttribute("books", bookService.findAllBooksWithSelectedStore(bookId, storeId));
        model.addAttribute("stores", storeService.getAllStores());
        return "list-book";
    }

    // Добавить книгу в избранное
    @PostMapping("/favorites/add")
    public String addToFavorites(@RequestParam Long bookId, Principal principal) {
        bookService.addBookToFavorites(bookId, principal.getName());
        return "redirect:/books/list-book";
    }

    // Удалить книгу из избранного
    @PostMapping("/favorites/remove")
    public String removeFromFavorites(@RequestParam Long bookId, Principal principal) {
        bookService.removeBookFromFavorites(bookId, principal.getName());
        return "redirect:/books/favorites";
    }

    // Отображение страницы избранного
    @GetMapping("/favorites")
    public String viewFavorites(Model model, Principal principal) {
        List<BookDto> favorites = bookService.getFavoriteBooks(principal.getName());
        model.addAttribute("books", favorites);
        return "favorites";
    }


}
