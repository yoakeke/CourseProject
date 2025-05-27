package ru.samarina.CourseProject.service;

import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.UserDto;
import ru.samarina.CourseProject.entity.User;

import java.util.List;

@Service
public interface UserService {

    void saveUser(UserDto userDto);
    User findById(Long id);
    User findUserByEmail(String email);
    void saveUserEntity(User user);
    List<UserDto> findAllUsers();
    void deleteUser(Long Id);
}


