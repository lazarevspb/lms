package ru.gbteam.lms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.mapper.RoleMapper;
import ru.gbteam.lms.controller.AdminController;
import ru.gbteam.lms.dto.RoleDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.RoleService;
import ru.gbteam.lms.service.UserService;
import ru.gbteam.lms.service.impl.UserDtoServiceImpl;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {
    private final UserDtoServiceImpl userDtoService;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String deleteCourse(Long id) {
        userDtoService.deleteById(id);
        return "redirect:/admin/users";
    }

    @Override
    public String newUserForm(Model model) {
        model.addAttribute("user", new UserWithPwdDto());
        return "new_user_form";
    }

    @Override
    @Transactional
    public String submitUserForm(UserWithPwdDto userDto, BindingResult bindingResult) {
        User user = userService.findById(userDto.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь", userDto.getId()));

        if (!userDto.getRoleDTO().equals(RoleMapper.roleMapper(user.getRoles()))) {
            user.getRoles().clear();
            user.setRoles(RoleMapper.roleDtoMapper(userDto.getRoleDTO()));
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        userService.save(user);
        return String.format("redirect:/admin/user/%d", userDto.getId());
    }

    @Override
    @Transactional
    public String createUser(UserWithPwdDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_user_form";
        }
        userDtoService.save(userDto);
        return "redirect:/admin/users";
    }

    @Override
    public Set<RoleDTO> rolesAttribute() {
        Set<Role> roleSet = new HashSet<>(roleService.findAll());
        return RoleMapper.roleMapper(roleSet);
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
