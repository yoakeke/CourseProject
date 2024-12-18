package ru.samarina.CourseProject.service;

import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.UserDto;
import ru.samarina.CourseProject.entity.User;

import java.util.List;


@Service
public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

}