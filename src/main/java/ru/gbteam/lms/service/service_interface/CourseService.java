package ru.gbteam.lms.service.service_interface;

import java.util.List;
import java.util.Optional;

import ru.gbteam.lms.model.Course;

public interface CourseService {
    List<Course> findAll();

    Optional<Course> findById(Long id);

    void save(Course course);

    void delete(Long id);
}
