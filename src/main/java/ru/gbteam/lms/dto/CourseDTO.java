package ru.gbteam.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gbteam.lms.annotation.ValidateCase;
import ru.gbteam.lms.enums.ValidateType;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class CourseDTO {
    private Long id;

    @NotBlank(message = "Название курса не может быть пустым")
    @ValidateCase(type = ValidateType.ANY, message = "Двойные пробелы запрещены")
    private String title;

    @NotBlank(message = "Автор курса должен быть заполен")
    private String author;
}
