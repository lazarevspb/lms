package ru.gbteam.lms.service;

import ru.gbteam.lms.dto.UserWithPwdDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoService {
    List<UserWithPwdDto> findAllDto();

    Optional<UserWithPwdDto> findDtoById(long id);

    void deleteById(long id);

    void save(UserWithPwdDto userWithPwdDto);
}
