package ru.gbteam.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbteam.lms.annotation.ValidateCase;
import ru.gbteam.lms.enums.ValidateType;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LessonDTO {

    private Long id;

    @NotBlank(message = "Название урока не может быть пустым")
    @ValidateCase(type = ValidateType.ANY, message = "Двойные пробелы запрещены")
    private String title;

    private String description;
    private String content;
    private String exercise;
    private Long moduleId;
    private String moduleTitle;
}
