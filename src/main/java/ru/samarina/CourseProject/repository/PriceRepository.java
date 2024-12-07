package ru.samarina.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.CourseProject.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    // Поиск цены книги по книге и магазину
    Price findByBookIdAndStoreId(Long bookId, Long storeId);
}