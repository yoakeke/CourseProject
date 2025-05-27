package ru.samarina.CourseProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.dto.UserDto;
import ru.samarina.CourseProject.entity.Role;
import ru.samarina.CourseProject.entity.User;
import ru.samarina.CourseProject.repository.RoleRepository;
import ru.samarina.CourseProject.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Регистрация пользователя
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Все пользователи имеют роль USER
        Role role = roleRepository.findByName("USER");
        // Если ролей нет - это первый пользователь, присваиваем роль админа
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    // Находим пользователя по почте
    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Находим всех пользователей
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    // Переносит данные из User в UserDto для отображения
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    // Находим пользователя по ID
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Админ добавляет пользователя
    @Override
    public void saveUserEntity(User user) {
        userRepository.save(user);
    }

    // Первый пользователь - админ
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    // Удаляем пользователя
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }


}