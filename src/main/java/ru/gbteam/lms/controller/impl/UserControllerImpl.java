package ru.gbteam.lms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.controller.UserController;
import ru.gbteam.lms.dto.UserDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.service.UserServiceFacade;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserServiceFacade userServiceFacade;

    @Override
    public String showUserProfile(Principal principal, Model model) {
        return userServiceFacade.getUserProfile(principal.getName(), model);
    }

    @Override
    public String updateUserProfile(Principal principal, UserDTO userDto, BindingResult result, Model model){
        return userServiceFacade.updateUserProfile(principal, userDto, result, model);
    }

    @Override
    public String showRegistrationForm(Model model) {
        UserWithPwdDto userWithPwdDto = new UserWithPwdDto();
        model.addAttribute("user", userWithPwdDto);
        return "registration";
    }

    @Override
    public String registerUserAccount(UserWithPwdDto userWithPwdDto, BindingResult result, Model model) {
        return userServiceFacade.registerUserAccount(userWithPwdDto, result, model);
    }

    @Override
    public String showChangeUserPwdForm(Principal principal, Model model) {
        return userServiceFacade.showChangeUserPwdForm(principal.getName(), model);
    }

    @Override
    public String changeUserPwd(Principal principal, UserWithPwdDto userWithPwdDto, Model model){
        return userServiceFacade.changeUserPwd(principal, userWithPwdDto, model);
    }
}
