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

    private final BookRepository bookRepository;
    private final BookStoreRepository bookStoreRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           BookStoreRepository bookStoreRepository,
                           StoreRepository storeRepository,
                           UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.bookStoreRepository = bookStoreRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    // Отображение списка всех книг
    @Override
    public List<BookDto> findAllBooks(String userEmail) {
        List<Book> books = bookRepository.findAll(); // Ищем все книги
        List<Long> favorites = getFavoriteBookIds(userEmail); // Ищем избранные книги пользователя

        // Преобразуем каждую книгу в DTO, добавляя флаг избранного
        return books.stream()
                .map(book -> mapToDto(book, favorites.contains(book.getId())))
                .collect(Collectors.toList());
    }

    // Добавление новой книги
    @Override
    public void addBookWithStores(String title, String author,
                                 List<Long> storeIds, List<Double> prices, List<Integer> quantities) {

        // Проверяем существует ли книга с таким названием и автором
        Book book = bookRepository.findByTitleAndAuthor(title, author)
                .orElseGet(() -> {
                    // Если нет — создаём новую
                    Book newBook = new Book();
                    newBook.setTitle(title);
                    newBook.setAuthor(author);
                    return bookRepository.save(newBook);
                });

        // Добавляем записи в таблицу для каждого магазина
        for (int i = 0; i < storeIds.size(); i++) {
            Long storeId = storeIds.get(i);
            Double price = prices.get(i);
            Integer quantity = quantities.get(i);

            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new RuntimeException("Store not found"));

            // Проверка, существует ли уже такая связка книга-магазин
            boolean alreadyExists = bookStoreRepository.existsByBookIdAndStoreId(book.getId(), storeId);
            if (!alreadyExists) {
                BookStore bookStore = new BookStore();
                bookStore.setBook(book);
                bookStore.setStore(store);
                bookStore.setPrice(price);
                bookStore.setQuantity(quantity);
                bookStoreRepository.save(bookStore);
            }
            // Если связка уже есть — пропускаем её
        }
    }


    // Редактирование книги
    @Override
    public void updateBookWithStores(BookDto bookDto,
                                     List<Long> storeIds,
                                     List<Double> prices,
                                     List<Integer> quantities) {
        // Получаем книгу по ID
        Book book = bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Обновляем название и автора
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        bookRepository.save(book);

        // Обновляем данные в BookStore или создаём новую связку, если она не существует
        for (int i = 0; i < storeIds.size(); i++) {
            Long storeId = storeIds.get(i);
            Double price = prices.get(i);
            Integer quantity = quantities.get(i);

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
    }

    // Удаляем книгу
    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id) // зачем проверка на существование
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }


    // ДЛЯ ИЗБРАННОГО (пользователи)

    // Добавление книги в список избранного
    @Override
    public void addBookToFavorites(Long bookId, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Добавляем книгу в избранное, если её там ещё нет
        if (!user.getFavoriteBooks().contains(book)) {
            user.getFavoriteBooks().add(book);
            userRepository.save(user);
        }
    }

    // Удаление книги из списка избранного
    @Override
    public void removeBookFromFavorites(Long bookId, String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        // Удаляем книгу по ID из списка избранного
        user.getFavoriteBooks().removeIf(b -> b.getId().equals(bookId));
        userRepository.save(user);
    }

    // Отображение избранных книг
    @Override
    public List<BookDto> getFavoriteBooks(String userEmail) {
        User user = userRepository.findByEmail(userEmail);

        // Каждую книгу помечаем как избранную
        return user.getFavoriteBooks().stream()
                .map(book -> mapToDto(book, true))
                .collect(Collectors.toList());
    }

    // Преобразует Book в BookDto, добавляя список магазинов и флаг избранного
    private BookDto mapToDto(Book book, boolean inFavorite) {
        List<BookStoreDto> stores = book.getBookStores().stream()
                .map(bs -> new BookStoreDto(
                        bs.getId(),
                        bs.getStore().getId(),
                        bs.getStore().getName(),
                        bs.getPrice(),
                        bs.getQuantity()))
                .collect(Collectors.toList());

        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), stores, inFavorite);
    }

    // Находим ID всех книг, добавленных пользователем в избранное
    private List<Long> getFavoriteBookIds(String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) return new ArrayList<>();
        User user = userRepository.findByEmail(userEmail);
        return user.getFavoriteBooks().stream().map(Book::getId).collect(Collectors.toList());
    }
}
