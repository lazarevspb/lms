package ru.gbteam.lms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    public Module(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        Module module = (Module) o;
        return Objects.equals(getId(), module.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
