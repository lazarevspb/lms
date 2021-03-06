package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;

import java.util.List;

import java.util.Optional;

public interface ModuleServiceFacade {

    Course findCourseById(Long id);

    Module findModuleById(Long id);

    void saveModule(Module module);

    void deleteModule(Long id);

    Page<Lesson> findLessonPaginated(Long id, Optional<Integer> page, Optional<Integer> size, String titlePrefix);

    List<Integer> getLessonPageNumbers(Long module_id, Optional<Integer> page, Optional<Integer> size, String titlePrefix);

    List<Lesson> findAllLessonsByModuleId(Long id);
}
