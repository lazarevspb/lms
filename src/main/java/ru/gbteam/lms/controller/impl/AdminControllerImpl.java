package ru.gbteam.lms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.controller.AdminController;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.RoleService;
import ru.gbteam.lms.service.UserService;
import ru.gbteam.lms.service.impl.UserDtoServiceImpl;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final UserDtoServiceImpl userDtoService;
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public String deleteCourse(Long id) {
        userDtoService.deleteById(id);
        return "redirect:/admin/users";
    }

    @Override
    public String courseForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @Override
    @Transactional
    public String submitUserForm(UserDto userDto, BindingResult bindingResult) {
        User user = userService.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("Пользователь", userDto.getId()));
        user.getRoles().clear();
        user.setRoles(userDto.getRoles());
        userService.save(user);
        return "redirect:/admin/users";
    }

    @Override
    public List<Role> rolesAttribute() {
        return roleService.findAll();
    }

    public String userForm(Model model, Long id) {
        UserDto user = userDtoService.findDtoById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь", id));
        model.addAttribute("user", user);
        return "user_form";
    }

    @Override
    public String userTable(Model model) {
        final List<UserDto> users = userDtoService.findAllDto();
        model.addAttribute("users", users);
        model.addAttribute("activePage", "admin");
        return "user_table";
    }
}
