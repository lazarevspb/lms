package ru.gbteam.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbteam.lms.mapper.RoleMapper;
import ru.gbteam.lms.annotation.ValidateCase;
import ru.gbteam.lms.enums.ValidateType;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Логин не должен быть пустым")
    @ValidateCase(type = ValidateType.LOGIN, message = "Поле Логин может содержать только латиницу и/или спец.символы")
    private String username;

    private String firstName;

    private String lastName;

    @ValidateCase(type = ValidateType.EMAIL, message = "Некорректный формат email")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;

    private Set<Course> courses;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(Long id, String username, String firstName, String lastName, String email, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = RoleMapper.roleMapper(roles);

    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = roles.stream().map(role -> new RoleDTO(role.getId(), role.getName())).collect(Collectors.toSet());
        this.courses = user.getCourses();
    }
}
