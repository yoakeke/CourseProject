package ru.samarina.CourseProject.service;

import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.entity.Book;

import java.util.List;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    void updateBookWithStore(BookDto bookDto, Long storeId, Double price, Integer quantity);
    void deleteBook(Long Id);
    List<BookDto> findAllBooks();
    void addBookWithStore(String title, String author, Long storeId, Double price, Integer quantity);
    List<BookDto> findAllBooksWithSelectedStore(Long selectedBookId, Long selectedStoreId);

    List<BookDto> findAllBooks(String userEmail);

    BookDto getBookById(Long id);
    void addBookToFavorites(Long bookId, String userEmail);
    void removeBookFromFavorites(Long bookId, String userEmail);
    List<BookDto> getFavoriteBooks(String userEmail);
}
