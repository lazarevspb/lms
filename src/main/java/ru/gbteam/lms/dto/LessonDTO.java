package ru.gbteam.lms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class LessonDTO {
    private Long id;
    private String title;
    private String description;
    private String content;
    private String exercise;
    private Long moduleId;
    private String moduleTitle;
}
