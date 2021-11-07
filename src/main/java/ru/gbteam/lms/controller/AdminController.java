package ru.gbteam.lms.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.model.Role;

import java.util.List;
import javax.validation.Valid;

@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public interface AdminController {
    @DeleteMapping("user/{id}")
    String deleteCourse(@PathVariable("id") Long id);

    @GetMapping("user/new")
    String newUserForm(Model model);

    @PostMapping("/user/save")
    String submitUserForm(@ModelAttribute("user") UserDto user, BindingResult bindingResult);

    @PostMapping("/user/create")
    String createUser(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult);

    @ModelAttribute("roles")
    List<Role> rolesAttribute();

    @GetMapping("user/{id}")
    String userForm(Model model, @PathVariable Long id);

    @GetMapping("/users")
    String userTable(Model model);
}
