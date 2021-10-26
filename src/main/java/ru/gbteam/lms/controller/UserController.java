package ru.gbteam.lms.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gbteam.lms.dto.UserDto;

import javax.validation.Valid;

public interface UserController {

    @GetMapping("/user/registration")
    String showRegistrationForm(Model model);

    @PostMapping("/user/registration")
    String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model);
}
