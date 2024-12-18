package ru.samarina.CourseProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;

    @NotEmpty(message = "Title should not be empty.")
    private String title;

    @NotEmpty(message = "Author should not be empty.")
    private String author; // Автор книги

    @Positive(message = "Quantity should be a positive number.")
    private Integer quantity; // Количество книги

    private List<BookStoreInfoDto> bookStoreInfos; // Список информации о ценах и количестве в магазинах

}
