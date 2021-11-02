package ru.gbteam.lms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.controller.UserController;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.service.UserServiceFacade;

@Controller
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserServiceFacade userServiceFacade;

    @Override
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @Override
    public String registerUserAccount(UserDto userDto, BindingResult result, Model model) {
        return userServiceFacade.registerUserAccount(userDto, result, model);
    }
}
