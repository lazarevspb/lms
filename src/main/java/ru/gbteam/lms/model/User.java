package ru.gbteam.lms.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Course> courses;

    @ManyToMany
    @ToString.Exclude
    private Set<Role> roles;

    @Column
    private String password;

    public User(Long id,
                String username,
                String password,
                Set<Role> roles,
                String email) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
