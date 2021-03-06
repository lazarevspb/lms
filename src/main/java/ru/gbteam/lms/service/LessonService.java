package ru.gbteam.lms.service;

import ru.gbteam.lms.model.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    List<Lesson> findAllByModuleId(Long id);

    Optional<Lesson> findById(Long id);

    void save(Lesson lesson);

    void deleteById(Long id);

    List<Lesson> findLessonsByModuleIdAndTitleLike(Long id, String search);
}
