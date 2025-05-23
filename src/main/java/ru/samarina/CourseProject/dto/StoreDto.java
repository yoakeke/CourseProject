package ru.samarina.CourseProject.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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
public class StoreDto {

    @NotNull(message = "Store ID should not be null.")
    private Long id;

    @NotEmpty(message = "Name should not be empty.")
    private String name;

    private List<BookStoreDto> books;

}
