package ru.samarina.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.CourseProject.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Поиск книг по названию
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Поиск книг по автору
    List<Book> findByAuthorContainingIgnoreCase(String author);
}