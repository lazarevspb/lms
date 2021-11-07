package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbteam.lms.model.Lesson;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByModuleId(Long id);

    Optional<Lesson> findById(Long id);

    List<Lesson> findAllByModuleIdAndTitleContainingIgnoreCase(Long id, String title);
}
