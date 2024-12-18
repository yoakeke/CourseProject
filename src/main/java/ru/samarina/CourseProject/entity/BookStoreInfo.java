package ru.samarina.CourseProject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookStoreInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // Связь с книгой

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // Связь с магазином

    @Positive(message = "Quantity should be a positive number.")
    private Integer quantity; // Количество книги в магазине

    @Positive(message = "Price should be a positive number.")
    private Double price; // Цена книги в магазине

}
