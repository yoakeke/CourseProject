package ru.samarina.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.samarina.CourseProject.dto.UserDto;
import ru.samarina.CourseProject.entity.Role;
import ru.samarina.CourseProject.entity.User;
import ru.samarina.CourseProject.service.RoleService;
import ru.samarina.CourseProject.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SecurityController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public SecurityController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Показ домашней страницы
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    // Показ формы логина
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Показ формы регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // Регистрация пользователей
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "На этот адрес электронной почты уже зарегистрирована учётная запись.");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        Role userRole = roleService.findByName("ROLE_USER");
        userDto.setRoles(Collections.singletonList(userRole));

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // Находим всех пользователей и роли
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<Role> roles = roleService.findAllRoles();
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "users";
    }

    // Меняем роль пользователю
    @PostMapping("/updateRole")
    public String updateUserRole(@RequestParam Long userId,
                                 @RequestParam String role) {
        User user = userService.findById(userId);
        if (user != null) {
            Role roleEntity = roleService.findByName(role);  // Получаем роль по имени
            List<Role> newRoles = new ArrayList<>();
            newRoles.add(roleEntity);  // Создаем новый список с единственной ролью
            user.setRoles(newRoles);  // Устанавливаем роль пользователю
            userService.saveUserEntity(user);  // Сохраняем изменения
        }
        return "redirect:/users";
    }

    // Админ создает пользователя
    @PostMapping("/addUser")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam String role) {
        Role roleEntity = roleService.findByName(role);  // Получаем роль по имени
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("defaultPassword"));  // Дефолтный пароль для созданных пользователей админом
        user.setRoles(Collections.singletonList(roleEntity));  // Устанавливаем роль пользователю
        userService.saveUserEntity(user);
        return "redirect:/users";
    }

    // Удаляем пользователя
    @PostMapping("/users/delete")
    public String deleteUserPost(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
