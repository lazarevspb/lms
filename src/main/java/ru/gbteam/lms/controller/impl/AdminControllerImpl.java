package ru.gbteam.lms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.controller.AdminController;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.service.RoleService;
import ru.gbteam.lms.service.impl.UserDtoServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final UserDtoServiceImpl userDtoService;
    private final RoleService roleService;

    @Override
    public String deleteCourse(Long id) {
        userDtoService.deleteById(id);
        return "redirect:/admin/users";
    }

    @Override
    public String courseForm(Model model) {
        model.addAttribute("user", new UserWithPwdDto());
        return "user_form";
    }

    @Override
    public String submitUserForm(UserWithPwdDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }
        userDtoService.save(user);
        return "redirect:/admin/users";
    }

    @Override
    public List<Role> rolesAttribute() {
        return roleService.findAll();
    }

    public String userForm(Model model, Long id) {
        UserWithPwdDto user = userDtoService.findDtoById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь", id));
        model.addAttribute("user", user);
        return "user_form";
    }

    @Override
    public String userTable(Model model) {
        final List<UserWithPwdDto> users = userDtoService.findAllDto();
        model.addAttribute("users", users);
        model.addAttribute("activePage", "admin");
        return "user_table";
    }
}
