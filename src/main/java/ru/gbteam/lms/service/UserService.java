package ru.gbteam.lms.service;

import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUsername(String username);

    List<User> findAll();

    List<User> findByCourse(Course course);

    Optional<User> findById(Long id);

    void save(User user);

    void delete(Long id);

    User registerNewUserAccount(UserDto userDto);

    List<User> findByCourseNotEqual(Course course);

}
