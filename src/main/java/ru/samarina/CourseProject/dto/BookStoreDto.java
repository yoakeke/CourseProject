package ru.samarina.CourseProject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.samarina.CourseProject.entity.Store;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreDto {

    @NotNull(message = "Book ID should not be null.")
    private Long bookId;

    @NotNull(message = "Store ID should not be null.")
    private Long storeId;

    @NotNull(message = "Price should not be null.")
    private Double price;

    @NotNull(message = "Quantity should not be null.")
    private Integer quantity;

    public BookStoreDto(Long storeId, Double price, Integer quantity) {
        this.storeId = storeId;
        this.price = price;
        this.quantity = quantity;
    }
}
