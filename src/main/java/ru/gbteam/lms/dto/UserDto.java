package ru.gbteam.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbteam.lms.annotation.ValidateCase;
import ru.gbteam.lms.enums.ValidateType;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.annotation.PasswordMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@PasswordMatches
public class UserDto {

    private Long id;

    @NotBlank(message = "Логин не должен быть пустым")
    @ValidateCase(type = ValidateType.LOGIN, message = "Поле Логин может содержать только латиницу и/или спец.символы")
    private String username;

    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    private String lastName;

    @ValidateCase(type = ValidateType.EMAIL, message = "Некорректный формат пароля")
    @NotBlank(message = "Email не должен быть пустым")
    private String email;

    private Set<Course> courses;

    private Set<Role> roles;

    @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
    @ValidateCase(type = ValidateType.PASSWORD, message = "Некорректный формат пароля")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
    private String matchingPassword;

    public UserDto(Long id, String username, String firstName,String lastName, String email, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.password = user.getPassword();
        this.courses = user.getCourses();
    }
}
