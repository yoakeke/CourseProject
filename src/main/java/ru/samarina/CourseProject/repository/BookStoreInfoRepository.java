package ru.samarina.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.CourseProject.entity.BookStoreInfo;


import java.util.List;
import java.util.Optional;

@Repository
public interface BookStoreInfoRepository extends JpaRepository<BookStoreInfo, Long> {

    // поиск по магазину
    List<BookStoreInfo> findByStoreId(Long storeId);
    // Найти запись по ID книги и ID магазина
    Optional<BookStoreInfo> findByBookIdAndStoreId(Long bookId, Long storeId);
    // поиск по книге
    List<BookStoreInfo> findByBookId(Long bookId);
}

