package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.dto.UserDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.exception.UserAlreadyExistException;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.MapperService;
import ru.gbteam.lms.service.UserService;
import ru.gbteam.lms.service.UserServiceFacade;

import java.security.Principal;
import java.util.Arrays;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceFacadeImpl implements UserServiceFacade {
    private final UserService userService;
    private final MapperService mapperService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String getUserProfile(String username, Model model) {
       User user = userService.findUserByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь", username));
       UserDTO userDTO = mapperService.toUserAuthDTO(user);
       model.addAttribute("user", userDTO);
       model.addAttribute("courses", userService.findCourses(user.getId()));
       return "user_profile";
    }

    @Override
    public String updateUserProfile(Principal principal, UserDTO userDTO, BindingResult result, Model model){

        if (result.hasErrors()) {
            String errorMessages = getErrorMessage(result);
            model.addAttribute("message", errorMessages);
            getUserProfile(principal.getName(), model);
            return "user_profile";
        }
        try {
            userService.updateUserProfile(principal, userDTO);
        } catch (UserAlreadyExistException ex) {
            model.addAttribute("message", "An account for that username/email already exists.");
            getUserProfile(principal.getName(), model);
            return "user_profile";
        }
        model.addAttribute("user", userDTO);
        return "redirect:/user";
    }

    @Override
    public String registerUserAccount(UserWithPwdDto userWithPwdDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            String errorMessages = getErrorMessage(result);
            model.addAttribute("message", errorMessages);
            return "registration";
        }
        try {
            userService.registerNewUserAccount(userWithPwdDto);
        } catch (UserAlreadyExistException ex) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return "registration";
        }
        model.addAttribute("user", userWithPwdDto);
        return "redirect:/login";
    }

    @Override
    public String showChangeUserPwdForm(String username, Model model){
        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("Пользователь", username));
        UserWithPwdDto userDTO = mapperService.toUserRegistrationDTO(user);
        model.addAttribute("user", userDTO);
        return "user_change_pass";
    }

    @Override
    public String changeUserPwd(Principal principal, UserWithPwdDto userWithPwdDto, Model model){
        if(!userWithPwdDto.getPassword().equals(userWithPwdDto.getMatchingPassword())){
            model.addAttribute("message", "Пароли не совпадают");
            return "user_change_pass";
        }
        User user = userService.findUserByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("Пользователь", principal.getName()));
        log.info("Обновляем пароль пользователя {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(userWithPwdDto.getPassword()));
        userService.save(user);
        return "user_change_pass";
    }

    @Override
    public String unAssignCourse(Principal principal, Long courseId){
        User user = userService.findUserByUsername(principal.getName())
                .orElseThrow(() -> new NotFoundException("Пользователь", principal.getName()));

        userService.unAssign(courseId, user.getId());
        return "redirect:/user";
    }

    private String getErrorMessage(BindingResult result){
        StringJoiner errorMessages = new StringJoiner(",");
        result.getAllErrors().stream()
                .filter(er -> {
                    if (!CollectionUtils.isEmpty(Arrays.asList(er.getCodes()))) {
                        return Arrays.asList(er.getCodes()).contains("PasswordMatches");
                    }
                    return false;
                })
                .forEach(er -> errorMessages.add(er.getDefaultMessage()));
        return errorMessages.toString();
    }

}
