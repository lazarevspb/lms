package ru.gbteam.lms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class CourseDTO {
    private Long id;
    private String author;
    private String title;
}
