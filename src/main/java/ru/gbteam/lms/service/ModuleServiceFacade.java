package ru.gbteam.lms.service;

import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;
import java.util.List;

public interface ModuleServiceFacade {

    Course findCourseById(Long id);

    Module findModuleById(Long id);

    void saveModule(Module module);

    void deleteModule(Long id);

    List<Lesson> findAllLessonsByModuleId(Long id);
}
