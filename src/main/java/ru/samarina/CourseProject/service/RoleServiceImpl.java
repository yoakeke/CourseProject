package ru.samarina.CourseProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.samarina.CourseProject.entity.Role;
import ru.samarina.CourseProject.repository.RoleRepository;
import ru.samarina.CourseProject.service.RoleService;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();  // Получаем все роли из базы данных
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);  // Получаем роль по имени
    }
}
