package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gbteam.lms.model.Lesson;
import java.util.List;
import java.util.Optional;

public interface LessonService {
    List<Lesson> findAllByModuleId(Long id);

    Optional<Lesson> findById(Long id);

    void save(Lesson lesson);

    void deleteById(Long id);
}
