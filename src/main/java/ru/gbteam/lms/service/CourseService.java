package ru.gbteam.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gbteam.lms.model.Course;

public interface CourseService {
    List<Course> findAll();

    Page<Course> findPaginated(Pageable pageable, String titlePrefix);

    Optional<Course> findById(Long id);

    void save(Course course);

    void delete(Long id);

    List<Course> findCoursesByTitleLike(String search);
}
