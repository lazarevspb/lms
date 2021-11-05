package ru.gbteam.lms.service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.dto.UserDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;

import java.security.Principal;

public interface UserServiceFacade {

    String getUserProfile(String username, Model model);

    String updateUserProfile(Principal principal, UserDTO userDto, BindingResult result, Model model);

    String registerUserAccount(UserWithPwdDto userWithPwdDto, BindingResult result, Model model);

    String showChangeUserPwdForm(String username, Model model);

    String changeUserPwd(Principal principal, UserWithPwdDto userWithPwdDto, Model model);
}
