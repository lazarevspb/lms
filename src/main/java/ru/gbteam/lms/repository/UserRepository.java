package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
