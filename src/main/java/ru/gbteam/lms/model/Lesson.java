package ru.gbteam.lms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Lesson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String content;

    private String exercise;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
