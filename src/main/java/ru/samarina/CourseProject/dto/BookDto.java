package ru.samarina.CourseProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.samarina.CourseProject.entity.Book;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @NotNull(message = "bookId should not be empty.")
    private Long id;

    @NotEmpty(message = "Title should not be empty.")
    private String title;

    @NotEmpty(message = "Author should not be empty.")
    private String author;

    private List<BookStoreDto> stores;

}
