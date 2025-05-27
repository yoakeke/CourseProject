package ru.samarina.CourseProject.service;

import ru.samarina.CourseProject.dto.BookDto;
import java.util.List;

public interface BookService {
    void updateBookWithStores(BookDto bookDto, List<Long> storeIds, List<Double> prices, List<Integer> quantities);
    void deleteBook(Long id);
    List<BookDto> findAllBooks(String userEmail);
    void addBookWithStores(String title, String author, List<Long> storeIds, List<Double> prices, List<Integer> quantities);
    void addBookToFavorites(Long bookId, String userEmail);
    void removeBookFromFavorites(Long bookId, String userEmail);
    List<BookDto> getFavoriteBooks(String userEmail);
}
