package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;

import ru.gbteam.lms.model.User;


import java.util.List;

public interface CourseServiceFacade {

    void unAssignUser(Long courseId, Long userId);

    void assignUser(Long courseId, Long userId);

    Course findCourseById(Long id);

    List<Module> findAllModulesByCourseId(Long id);

    void deleteCourse(Long id);

    void saveCourse(Course course);

    List<Course> findAllCourses();

    List<User> findAllUsers();

    Page<Course> findPaginated(Pageable pageable, String titlePrefix);

}
