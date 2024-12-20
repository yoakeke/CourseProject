package ru.samarina.CourseProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title should not be empty.")
    private String title;

    @NotEmpty(message = "Author should not be empty.")
    private String author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookStore> bookStores = new ArrayList<>(); // Используем List вместо Set

}
