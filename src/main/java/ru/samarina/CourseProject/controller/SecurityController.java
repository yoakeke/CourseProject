package ru.samarina.CourseProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.samarina.CourseProject.dto.BookDto;
import ru.samarina.CourseProject.dto.UserDto;
import ru.samarina.CourseProject.entity.Role;
import ru.samarina.CourseProject.entity.User;
import ru.samarina.CourseProject.service.BookService;
import ru.samarina.CourseProject.service.RoleService;
import ru.samarina.CourseProject.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class SecurityController {

    private UserService userService;
    private BookService bookService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    public SecurityController(UserService userService, BookService bookService, RoleService roleService) {
        this.userService = userService;
        this.bookService = bookService;
        this.roleService = roleService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

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

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<Role> roles = roleService.findAllRoles();
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("roles", roles);
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/updateRole")
    public String updateUserRole(@RequestParam Long userId,
                                 @RequestParam String role) {
        User user = userService.findById(userId);
        if (user != null) {
            Role roleEntity = roleService.findByName(role);  // получаем роль по имени
            List<Role> newRoles = new ArrayList<>();
            newRoles.add(roleEntity);  // создаем новый список с единственной ролью
            user.setRoles(newRoles);  // устанавливаем роль пользователю
            userService.saveUserEntity(user);  // сохраняем изменения
        }
        return "redirect:/users";
    }


    @GetMapping("/books")
    public String listBooks(Model model) {
        List<BookDto> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "list-book";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam String role) {
        Role roleEntity = roleService.findByName(role);  // получаем роль по имени
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("defaultPassword"));  // Заменить или сгенерировать пароль
        user.setRoles(Collections.singletonList(roleEntity));  // устанавливаем роль пользователю
        userService.saveUserEntity(user);
        return "redirect:/users";
    }
}
