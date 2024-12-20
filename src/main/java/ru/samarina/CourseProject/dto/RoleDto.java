package ru.samarina.CourseProject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @NotNull(message = "Role ID should not be null.")
    private Long id;

    @NotEmpty(message = "Role name should not be empty.")
    private String name;
}
