package ru.gbteam.lms.service;

import ru.gbteam.lms.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUsername(String username);

    List<User> findAll();

    Optional<User> findById(Long id);

    void save(User user);

    void delete(Long id);

}