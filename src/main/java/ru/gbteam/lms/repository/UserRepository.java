package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbteam.lms.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String email);

}
