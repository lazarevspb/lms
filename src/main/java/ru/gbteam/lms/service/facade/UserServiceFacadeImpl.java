package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.exception.UserAlreadyExistException;
import ru.gbteam.lms.service.UserService;
import ru.gbteam.lms.service.UserServiceFacade;

import java.util.Arrays;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Service
public class UserServiceFacadeImpl implements UserServiceFacade {
    private final UserService userService;

    public String registerUserAccount(UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            StringJoiner errorMessages = new StringJoiner(",");
            result.getAllErrors().stream()
                    .filter(er -> Arrays.stream(er.getCodes()).anyMatch(code -> code.equals("PasswordMatches")))
                    .forEach(er -> errorMessages.add(er.getDefaultMessage()));
            model.addAttribute("message", errorMessages);
            return "registration";
        }
        try {
            userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException ex) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return "registration";
        }
        model.addAttribute("user", userDto);
        model.addAttribute("message", "An account is created");
        return "registration";
    }

}