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

    // Отображение списка всех книг
    @GetMapping("/list-book")
    public String listBooks(Model model, Principal principal) {
        String userEmail = (principal != null) ? principal.getName() : null;

        List<BookDto> books = bookService.findAllBooks(userEmail);

        model.addAttribute("books", books);
        model.addAttribute("stores", storeService.getAllStores());

        // Статус избранного для пользователей
        BookDto emptyBookDto = new BookDto();
        emptyBookDto.setInFavorite(false); // По умолчанию не в избранном
        model.addAttribute("bookDto", emptyBookDto);

        return "list-book";
    }


    // ФУНКЦИИ ДЛЯ АДМИНА

    // Добавление новой книги
    @PostMapping("/add")
    public String addBook(@RequestParam String title,
                          @RequestParam String author,
                          @RequestParam List<Long> storeIds,
                          @RequestParam List<Double> prices,
                          @RequestParam List<Integer> quantities) {

        bookService.addBookWithStores(title, author, storeIds, prices, quantities);
        return "redirect:/books/list-book";
    }

    // Редактирование книги
    @PostMapping("/edit")
    public String editBook(@ModelAttribute BookDto book,
                           @RequestParam List<Long> storeIds,
                           @RequestParam List<Double> prices,
                           @RequestParam List<Integer> quantities) {

        bookService.updateBookWithStores(book, storeIds, prices, quantities);
        return "redirect:/books/list-book";
    }

    // Удаление книги
    @PostMapping("/delete")
    public String deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return "redirect:/books/list-book";
    }


    // ФУНКЦИИ ДЛЯ ПОЛЬЗОВАТЕЛЕЙ

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
