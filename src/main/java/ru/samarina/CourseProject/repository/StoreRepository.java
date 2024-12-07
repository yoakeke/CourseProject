package ru.samarina.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.CourseProject.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // Поиск магазина по имени
    Store findByName(String name);
}