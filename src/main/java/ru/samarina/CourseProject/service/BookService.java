package ru.samarina.CourseProject.service;

import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.dto.BookStoreInfoDto;
import java.util.List;

@Service
public interface BookService {

    List<BookDto> findAllBooks();
    BookDto findBookById(Long id);
    void saveBook(BookDto bookDto);
    List<BookStoreInfoDto> findBookStores(Long bookId);
    void updateBookStoreInfo(BookStoreInfoDto bookStoreInfoDto);
}