package ru.samarina.CourseProject.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty.")
    private String Name;

    @NotEmpty(message = "Address should not be empty.")
    private String address;
}