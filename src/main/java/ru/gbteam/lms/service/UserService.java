package ru.gbteam.lms.service;

import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.dto.UserDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUsername(String username);

    List<User> findAll();

    List<User> findByCourse(Course course);

    Optional<User> findById(Long id);

    void save(User user);

    void delete(Long id);

    User registerNewUserAccount(UserWithPwdDto userWithPwdDto);

    User updateUserProfile(Principal principal, UserDTO userDTO);

    List<User> findByCourseNotEqual(Course course);

    void unAssign(Long courseId, Long userId);

    void assign(Long courseId, Long userId);

    List<Course> findCourses(Long userId);
}
