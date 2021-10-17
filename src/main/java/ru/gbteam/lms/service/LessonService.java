package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> findAllByModuleId(Long id);

    Page<Lesson> findPaginated(Long moduleId, Pageable pageable);

    LessonDTO findLessonById(Long id);

    void save(LessonDTO lessonDTO);

    Long delete(Long id);
}
