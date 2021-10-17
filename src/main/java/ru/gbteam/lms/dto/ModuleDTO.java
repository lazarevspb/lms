package ru.gbteam.lms.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ModuleDTO {
    private Long id;
    private String title;
    private String text;
    private Long courseId;
}
