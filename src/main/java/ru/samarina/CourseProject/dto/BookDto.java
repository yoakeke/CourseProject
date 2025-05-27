package ru.samarina.CourseProject.dto;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private List<BookStoreDto> stores = new ArrayList<>();
    private boolean inFavorite;

    public BookDto(Long id, String title, String author, List<BookStoreDto> stores, boolean inFavorite) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.stores = stores;
        this.inFavorite = inFavorite;
    }
}
