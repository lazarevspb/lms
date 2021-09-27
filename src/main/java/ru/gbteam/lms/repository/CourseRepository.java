package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbteam.lms.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
