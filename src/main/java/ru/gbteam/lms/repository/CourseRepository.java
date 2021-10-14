package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbteam.lms.model.Course;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCoursesByTitleLike(String search);
}
