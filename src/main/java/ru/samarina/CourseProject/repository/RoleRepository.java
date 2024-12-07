package ru.samarina.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.CourseProject.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Найти роль по имени
    Role findByName(String name);
}