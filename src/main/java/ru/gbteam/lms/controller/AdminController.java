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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserDtoServiceImpl userDtoService;
    private final RoleService roleService;

    @DeleteMapping("user/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        userDtoService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("user/new")
    public String courseForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @PostMapping("/user/save")
    public String submitUserForm(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }
        userDtoService.save(user);
        return "redirect:/admin/users";
    }

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return roleService.findAll();
    }

    @GetMapping("user/{id}")
    public String userForm(Model model, @PathVariable Long id) {
        UserDto user = userDtoService.findDtoById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь", id));
        model.addAttribute("user", user);
        return "user_form";
    }

    @GetMapping("/users")
    public String userTable(Model model) {
        final List<UserDto> users = userDtoService.findAllDto();
        model.addAttribute("users", users);
        model.addAttribute("activePage", "admin");
        return "user_table";
    }
}
