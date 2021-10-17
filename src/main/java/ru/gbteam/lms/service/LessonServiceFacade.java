package ru.gbteam.lms.service;

import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.model.Lesson;

public interface LessonServiceFacade {

    LessonDTO mapLessonToDto(Long id);

    void saveLesson(LessonDTO lessonDTO);

    Lesson findLessonById(Long id);

    void deleteLesson(Long id);
}
