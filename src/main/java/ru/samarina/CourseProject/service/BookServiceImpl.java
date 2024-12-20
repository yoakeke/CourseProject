package ru.samarina.CourseProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.entity.Book;
import ru.samarina.CourseProject.repository.BookRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> findAllBooks() {
        // Получаем все книги
        List<Book> books = bookRepository.findAll();

        // Преобразуем книги в BookDto
        return books.stream()
                .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(), null)) // Преобразуем в DTO
                .collect(Collectors.toList());
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        // Преобразуем BookDto в сущность Book
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());

        // Сохраняем книгу в базе данных
        Book savedBook = bookRepository.save(book);

        // Возвращаем BookDto с ID созданной книги
        return new BookDto(savedBook.getId(), savedBook.getTitle(), savedBook.getAuthor(), null);
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        // Находим существующую книгу
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        // Обновляем данные
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());

        // Сохраняем обновленные данные
        Book updatedBook = bookRepository.save(book);

        // Возвращаем обновленные данные в DTO
        return new BookDto(updatedBook.getId(), updatedBook.getTitle(), updatedBook.getAuthor(), null);
    }

    @Override
    public void deleteBook(Long id) {
        // Проверяем существование книги
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        // Удаляем книгу
        bookRepository.delete(book);
    }
}
