package ru.samarina.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.CourseProject.entity.Store;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByNameContainingIgnoreCase(String keyword);
}
