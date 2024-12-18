package ru.samarina.CourseProject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.dto.BookStoreInfoDto;
import ru.samarina.CourseProject.entity.Book;
import ru.samarina.CourseProject.entity.BookStoreInfo;
import ru.samarina.CourseProject.repository.BookRepository;
import ru.samarina.CourseProject.repository.BookStoreInfoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookStoreInfoRepository bookStoreInfoRepository;

    public BookServiceImpl(BookRepository bookRepository, BookStoreInfoRepository bookStoreInfoRepository) {
        this.bookRepository = bookRepository;
        this.bookStoreInfoRepository = bookStoreInfoRepository;
    }

    @Override
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> mapToBookDto(book))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return mapToBookDto(book);
    }

    @Override
    public void saveBook(BookDto bookDto) {
        Book book = mapToBook(bookDto);
        bookRepository.save(book);
    }

    @Override
    public List<BookStoreInfoDto> findBookStores(Long bookId) {
        List<BookStoreInfo> bookStoreInfos = bookStoreInfoRepository.findByBookId(bookId);
        return bookStoreInfos.stream()
                .map(this::mapToBookStoreInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBookStoreInfo(BookStoreInfoDto bookStoreInfoDto) {
        BookStoreInfo bookStoreInfo = bookStoreInfoRepository.findByBookIdAndStoreId(
                bookStoreInfoDto.getBookId(),
                bookStoreInfoDto.getStoreId()
        ).orElseThrow(() -> new RuntimeException("BookStoreInfo not found"));

        bookStoreInfo.setPrice(bookStoreInfoDto.getPrice());
        bookStoreInfo.setQuantity(bookStoreInfoDto.getQuantity());
        bookStoreInfoRepository.save(bookStoreInfo);
    }

    private BookDto mapToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        return bookDto;
    }

    private Book mapToBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        return book;
    }

    private BookStoreInfoDto mapToBookStoreInfoDto(BookStoreInfo bookStoreInfo) {
        return new BookStoreInfoDto(
                        bookStoreInfo.getBook().getId(),
                        bookStoreInfo.getStore().getId(),
                bookStoreInfo.getQuantity(),
                bookStoreInfo.getPrice()
                );
    }
}
