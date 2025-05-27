package ru.samarina.CourseProject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookStoreDto {
    private Long id;
    private Long storeId;
    private String storeName;
    private Double price;
    private Integer quantity;

    public BookStoreDto(Long storeId, String storeName, Double price, Integer quantity) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.price = price;
        this.quantity = quantity;
    }
}
