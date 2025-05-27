package ru.samarina.CourseProject.service;

import ru.samarina.CourseProject.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
    Role findByName(String name);
}
