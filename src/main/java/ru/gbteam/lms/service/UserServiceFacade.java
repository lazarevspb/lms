package ru.gbteam.lms.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.dto.UserDto;

public interface UserServiceFacade {

    String registerUserAccount(UserDto userDto, BindingResult result, Model model);
}
