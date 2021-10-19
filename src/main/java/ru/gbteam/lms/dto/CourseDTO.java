package ru.gbteam.lms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CourseDTO {
    private Long id;
    private String author;
    private String title;
}
