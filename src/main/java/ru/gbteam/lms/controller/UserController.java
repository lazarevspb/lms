package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.service.RoleService;
import ru.gbteam.lms.service.impl.UserDtoServiceImpl;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserDtoServiceImpl userDtoService;
    private final RoleService roleService;

    @PostMapping
    public String submitUserForm(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }
        userDtoService.save(user);
        return "redirect:/user";
    }

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public String userForm(Model model, @PathVariable Long id) {
        UserDto user = userDtoService.findDtoById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь", id));
        model.addAttribute("user", user);
        return "user_form";
    }

    @GetMapping
    public String userTable(Model model) {
        final List<UserDto> users = userDtoService.findAllDto();
        model.addAttribute("users", users);
        return "user_table";
    }
}
