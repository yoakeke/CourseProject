package ru.samarina.CourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.CourseProject.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}