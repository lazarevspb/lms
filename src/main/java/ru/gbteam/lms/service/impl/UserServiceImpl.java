package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.UserDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.enums.UserRole;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.exception.UserAlreadyExistException;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.RoleRepository;
import ru.gbteam.lms.repository.UserRepository;
import ru.gbteam.lms.service.MapperService;
import ru.gbteam.lms.service.UserService;

import java.security.Principal;
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
    public User registerNewUserAccount(UserWithPwdDto userWithPwdDto) {
        userWithPwdDto.getRoles().add(roleRepository.getById(UserRole.ROLE_STUDENT.getId()));
        User user = mapperService.fromUserRegistrationDTO(userWithPwdDto);
        checkError(Optional.empty(), user);
        log.info("Сохраняем пользователя с логином {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public User updateUserProfile(Principal principal, UserDTO userDTO){
        User user = mapperService.fromUserAuthDTO(userDTO);
        User userBeforeUpdate = userRepository.findUserByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("Пользователь", principal.getName()));
        user.setRoles(userBeforeUpdate.getRoles());
        checkError(Optional.of(principal), user);
        log.info("Обновляем пользователя {}", user.getId());
        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean loginExists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    private void checkError(Optional<Principal> principal, User user){
        User userBeforeUpdate;
        String userMail = null;
        String username = null;
        if(principal.isPresent()){
            userBeforeUpdate = userRepository.findUserByUsername(principal.get().getName())
                    .orElseThrow(() -> new NotFoundException("Пользователь", principal.get().getName()));
            userMail = userBeforeUpdate.getEmail();
            username = userBeforeUpdate.getUsername();
        }
        if (emailExists(user.getEmail()) && !user.getEmail().equals(userMail)) {
            log.warn("Пользователь с таким email {} уже существует", user.getEmail());
            throw new UserAlreadyExistException("There is an account with that email address: " + user.getEmail(),
                    user.getUsername());
        }

        if (loginExists(user.getUsername()) && !user.getUsername().equals(username)) {
            log.warn("Пользователь с таким логином {} уже существует", user.getUsername());
            throw new UserAlreadyExistException("There is an account with that username address: " + user.getUsername(),
                    user.getUsername());
        }
    }

}
