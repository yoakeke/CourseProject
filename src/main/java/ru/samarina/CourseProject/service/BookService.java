package ru.samarina.CourseProject.service;

import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.entity.Book;

import java.util.List;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    BookDto updateBook(Long Id, BookDto bookDto);
    void deleteBook(Long Id);
    List<BookDto> findAllBooks();
}
