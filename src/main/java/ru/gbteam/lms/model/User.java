package ru.gbteam.lms.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Course> courses;

    @ManyToMany
    @ToString.Exclude
    private Set<Role> roles;

    @Column
    private String password;
}
