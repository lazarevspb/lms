package ru.gbteam.lms.service;

import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.model.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> findAllByModuleId(Long id);

    LessonDTO findLessonById(Long id);

    void save(LessonDTO lessonDTO);

    Long delete(Long id);
}
