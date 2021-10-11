package ru.gbteam.lms.service;

import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import java.util.List;

public interface CourseServiceFacade {

    void unAssignUser(Long courseId, Long userId);

    void assignUser(Long courseId, Long userId);

    Course findCourseById(Long id);

    List<Module> findAllModulesByCourseId(Long id);

    void deleteCourse(Long id);

    void saveCourse(Course course);

    List<Course> findAllCourses();

}
