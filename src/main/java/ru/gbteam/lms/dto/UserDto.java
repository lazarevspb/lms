package ru.gbteam.lms.dto;

import lombok.*;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.model.User;

import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private Set<Course> courses;

    private Set<Role> roles;

    private String password;

    public UserDto(Long id, String username, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.password = password;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.password = user.getPassword();
        this.courses = user.getCourses();
    }
}