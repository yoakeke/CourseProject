package ru.samarina.CourseProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreDto {
    private Long storeId;
    private String storeName;
    private Double price;
    private Integer quantity;
}
