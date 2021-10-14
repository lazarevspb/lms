package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import java.util.List;
import java.util.Optional;

public interface CourseServiceFacade {

    void unAssignUser(Long courseId, Long userId);

    void assignUser(Long courseId, Long userId);

    Course findCourseById(Long id);

    List<Module> findAllModulesByCourseId(Long id);

    void deleteCourse(Long id);

    void saveCourse(Course course);

    List<Course> findAllCourses();

    Page<Course> findPaginated(Optional<Integer> page, Optional<Integer> size);

    List<Integer> getPageNumbers(Optional<Integer> page, Optional<Integer> size, Model model);

}
