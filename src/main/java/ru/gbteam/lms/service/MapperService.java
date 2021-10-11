package ru.gbteam.lms.service;

import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;

public interface MapperService {
    Lesson fromDTO(LessonDTO dto);
    LessonDTO toDTO(Lesson model);
    Course fromDTO(CourseDTO dto);
    CourseDTO toDTO(Course model);
}
