package ru.samarina.CourseProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreInfoDto {
    @NotEmpty(message = "bookId should not be empty.")
    private Long bookId;
    @NotEmpty(message = "storeId should not be empty.")
    private Long storeId;
    @Positive(message = "Price should be a positive number.")
    private Integer quantity;
    @Positive(message = "Price should be a positive number.")
    private Double price;

}
