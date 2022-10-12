package ru.gbteam.lms.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String author;
    @Column
    private String title;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Module> modules;

    @ManyToMany
    private Set<User> users;

    @OneToOne(mappedBy = "course", cascade = CascadeType.REMOVE)
    private CourseImage courseImage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
