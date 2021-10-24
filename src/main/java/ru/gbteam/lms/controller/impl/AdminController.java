package ru.gbteam.lms.controller.impl;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.model.Role;

import java.util.List;
import javax.validation.Valid;

@RequestMapping("/admin")
public interface AdminController {
    @DeleteMapping("user/{id}")
    String deleteCourse(@PathVariable("id") Long id);

    @GetMapping("user/new")
    String courseForm(Model model);

    @PostMapping("/user/save")
    String submitUserForm(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult);

    @ModelAttribute("roles")
    List<Role> rolesAttribute();

    @GetMapping("user/{id}")
    String userForm(Model model, @PathVariable Long id);

    @GetMapping("/users")
    String userTable(Model model);
}
