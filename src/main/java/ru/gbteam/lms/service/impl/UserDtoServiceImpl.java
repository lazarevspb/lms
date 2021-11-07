package ru.gbteam.lms.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.UserRepository;
import ru.gbteam.lms.service.UserDtoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDtoServiceImpl implements UserDtoService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public List<UserWithPwdDto> findAllDto() {
        return userRepository.findAll().stream()
                .map(usr -> new UserWithPwdDto(usr.getId(),
                        usr.getUsername(),
                        usr.getFirstName(),
                        usr.getLastName(),
                        usr.getEmail(),
                        "",
                        usr.getRoles()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserWithPwdDto> findDtoById(long id) {
        return userRepository.findById(id)
                .map(usr -> new UserWithPwdDto(usr.getId(),
                        usr.getUsername(),
                        usr.getFirstName(),
                        usr.getLastName(),
                        usr.getEmail(),
                        "",
                        usr.getRoles()));
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void save(UserWithPwdDto userDto) {
        userRepository.save(new User(userDto.getId(),
                userDto.getUsername(),
                encoder.encode(userDto.getPassword()),
                userDto.getRoles(),
                userDto.getEmail()
        ));
    }
}
