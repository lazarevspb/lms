package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUsername(String username);

    List<User> findAll();

    Optional<User> findById(Long id);

    void save(User user);

    void delete(Long id);

    User registerNewUserAccount(UserDto userDto);

}
