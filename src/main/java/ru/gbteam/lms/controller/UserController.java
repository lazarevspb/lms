package ru.gbteam.lms.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gbteam.lms.dto.UserDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;

import java.security.Principal;
import javax.validation.Valid;

@RequestMapping("/user")
public interface UserController {

    @GetMapping
    String showUserProfile(Principal principal, Model model);

    @PostMapping
    String updateUserProfile(Principal principal, @ModelAttribute("user") @Valid UserDTO userRegistrationDto, BindingResult result, Model model);

    @GetMapping("/registration")
    String showRegistrationForm(Model model);

    @PostMapping("/registration")
    String registerUserAccount(@ModelAttribute("user") @Valid UserWithPwdDto userWithPwdDto, BindingResult result, Model model);

    @GetMapping("/change-password")
    String showChangeUserPwdForm(Principal principal, Model model);

    @PostMapping("/change-password")
    String changeUserPwd(Principal principal, @ModelAttribute("user") UserWithPwdDto userWithPwdDto, Model model);

    @GetMapping("/{courseId}/unassign_course")
    String unAssignCourse(Principal principal, @PathVariable("courseId") Long courseId);
}
