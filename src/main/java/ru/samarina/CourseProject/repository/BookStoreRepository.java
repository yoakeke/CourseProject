package ru.samarina.CourseProject.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.samarina.CourseProject.entity.BookStore;

import java.util.List;
import java.util.Optional;

public interface BookStoreRepository extends JpaRepository<BookStore, Long> {

    Optional<BookStore> findByBookIdAndStoreId(Long bookId, Long storeId);
    void deleteAllByBookId(Long bookId);
    List<BookStore> findByBookId(Long bookId);  // Метод для получения всех записей BookStore по bookId

}
