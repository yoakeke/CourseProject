package ru.samarina.CourseProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.dto.BookStoreDto;
import ru.samarina.CourseProject.entity.Book;
import ru.samarina.CourseProject.entity.BookStore;
import ru.samarina.CourseProject.entity.Store;
import ru.samarina.CourseProject.entity.User;
import ru.samarina.CourseProject.repository.BookRepository;
import ru.samarina.CourseProject.repository.BookStoreRepository;
import ru.samarina.CourseProject.repository.StoreRepository;
import ru.samarina.CourseProject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BookDto> findAllBooks() {
        return findAllBooks(null);
    }

    @Override
    public List<BookDto> findAllBooks(String userEmail) {
        List<Book> books = bookRepository.findAll();

        // Получаем список ID избранных книг пользователя
        List<Long> favoriteBookIds;
        if (userEmail != null && !userEmail.isEmpty()) {
            User user = userRepository.findByEmail(userEmail);
            favoriteBookIds = user.getFavoriteBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
        } else {
            favoriteBookIds = new ArrayList<>();
        }

        return books.stream().map(book -> {
            List<BookStoreDto> storeDtos = book.getBookStores().stream().map(bs -> {
                BookStoreDto dto = new BookStoreDto();
                dto.setStoreId(bs.getStore().getId());
                dto.setStoreName(bs.getStore().getName());
                dto.setPrice(bs.getPrice());
                dto.setQuantity(bs.getQuantity());
                return dto;
            }).collect(Collectors.toList());

            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setStores(storeDtos);
            bookDto.setInFavorite(favoriteBookIds.contains(book.getId()));

            return bookDto;
        }).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        List<BookStoreDto> storeDtos = book.getBookStores().stream().map(bookStore -> {
            BookStoreDto dto = new BookStoreDto();
            dto.setStoreId(bookStore.getStore().getId());
            dto.setStoreName(bookStore.getStore().getName());
            dto.setPrice(bookStore.getPrice());
            dto.setQuantity(bookStore.getQuantity());
            return dto;
        }).collect(Collectors.toList());

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setStores(storeDtos);
        bookDto.setInFavorite(false); // По умолчанию не в избранном

        return bookDto;
    }

    @Override
    public void addBookWithStore(String title, String author, Long storeId, Double price, Integer quantity) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book = bookRepository.save(book);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        BookStore bookStore = new BookStore();
        bookStore.setBook(book);
        bookStore.setStore(store);
        bookStore.setPrice(price);
        bookStore.setQuantity(quantity);

        bookStoreRepository.save(bookStore);
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        Book savedBook = bookRepository.save(book);

        BookDto resultDto = new BookDto();
        resultDto.setId(savedBook.getId());
        resultDto.setTitle(savedBook.getTitle());
        resultDto.setAuthor(savedBook.getAuthor());
        resultDto.setStores(new ArrayList<>()); // или загрузи Stores, если нужно
        resultDto.setInFavorite(false); // по умолчанию

        return resultDto;
    }

    @Override
    public void updateBookWithStore(BookDto bookDto, Long storeId, Double price, Integer quantity) {
        Book book = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        bookRepository.save(book);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        BookStore bookStore = bookStoreRepository.findByBookIdAndStoreId(book.getId(), storeId)
                .orElseGet(() -> {
                    BookStore bs = new BookStore();
                    bs.setBook(book);
                    bs.setStore(store);
                    return bs;
                });

        bookStore.setPrice(price);
        bookStore.setQuantity(quantity);
        bookStoreRepository.save(bookStore);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> findAllBooksWithSelectedStore(Long selectedBookId, Long selectedStoreId) {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(book -> {
            List<BookStoreDto> storeDtos = book.getBookStores().stream().map(bs -> {
                BookStoreDto dto = new BookStoreDto();
                dto.setStoreId(bs.getStore().getId());
                dto.setStoreName(bs.getStore().getName());
                dto.setPrice(bs.getPrice());
                dto.setQuantity(bs.getQuantity());
                return dto;
            }).collect(Collectors.toList());

            BookDto bookDto = new BookDto();
            bookDto.setId(book.getId());
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setStores(storeDtos);
            bookDto.setInFavorite(false); // по умолчанию

            return bookDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void addBookToFavorites(Long bookId, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        user.getFavoriteBooks().add(book);
        userRepository.save(user);
    }

    @Override
    public void removeBookFromFavorites(Long bookId, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        user.getFavoriteBooks().removeIf(b -> b.getId().equals(bookId));
        userRepository.save(user);
    }

    @Override
    public List<BookDto> getFavoriteBooks(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        return user.getFavoriteBooks().stream()
                .map(book -> {
                    List<BookStoreDto> stores = book.getBookStores().stream().map(bs -> {
                        BookStoreDto dto = new BookStoreDto();
                        dto.setStoreId(bs.getStore().getId());
                        dto.setStoreName(bs.getStore().getName());
                        dto.setPrice(bs.getPrice());
                        dto.setQuantity(bs.getQuantity());
                        return dto;
                    }).collect(Collectors.toList());

                    BookDto bookDto = new BookDto();
                    bookDto.setId(book.getId());
                    bookDto.setTitle(book.getTitle());
                    bookDto.setAuthor(book.getAuthor());
                    bookDto.setStores(stores);
                    bookDto.setInFavorite(true); // всегда true, так как это избранное

                    return bookDto;
                })
                .collect(Collectors.toList());
    }
}