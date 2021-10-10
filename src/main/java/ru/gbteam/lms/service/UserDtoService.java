package ru.gbteam.lms.service;

import ru.gbteam.lms.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoService {
    List<UserDto> findAllDto();

    Optional<UserDto> findDtoById(long id);

    void deleteById(long id);

    void save(UserDto userDto);
}
