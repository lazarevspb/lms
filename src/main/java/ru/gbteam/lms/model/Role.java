package ru.gbteam.lms.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch=FetchType.EAGER)
    private Set<User> users;

    public Role(String name) {
        this.name = name;
    }


}