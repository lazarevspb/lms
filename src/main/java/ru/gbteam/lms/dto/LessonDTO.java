package ru.gbteam.lms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String exercise;
    private Long moduleId;
    private String moduleTitle;
}
