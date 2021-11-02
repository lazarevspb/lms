package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.enums.UserRole;
import ru.gbteam.lms.exception.UserAlreadyExistException;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.RoleRepository;
import ru.gbteam.lms.repository.UserRepository;
import ru.gbteam.lms.service.MapperService;
import ru.gbteam.lms.service.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperService mapperService;
    private final RoleRepository roleRepository;

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            log.warn("Пользователь с таким email {} уже существует", userDto.getEmail());
            throw new UserAlreadyExistException("There is an account with that email address: " + userDto.getEmail(),
                    userDto.getUsername());
        }

        if (loginExists(userDto.getUsername())) {
            log.warn("Пользователь с таким логином {} уже существует", userDto.getUsername());
            throw new UserAlreadyExistException("There is an account with that username address: " + userDto.getUsername(),
                    userDto.getUsername());
        }
        userDto.getRoles().add(roleRepository.getById(UserRole.ROLE_STUDENT.getId()));
        User user = mapperService.fromDTO(userDto);
        log.info("Сохраняем пользователя с логином {}", user.getUsername());
        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean loginExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

}
