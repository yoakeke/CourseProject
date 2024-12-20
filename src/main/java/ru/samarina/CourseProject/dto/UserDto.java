package ru.samarina.CourseProject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "User ID should not be null.")
    private long id;

    @NotEmpty(message = "First name should not be empty.")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty.")
    private String lastName;

    @NotEmpty(message = "Email should not be empty.")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty.")
    private String password;

    private List<String> roles;
}