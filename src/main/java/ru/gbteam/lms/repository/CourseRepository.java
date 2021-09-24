package ru.gbteam.lms.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.gbteam.lms.model.Course;

public interface CourseRepository {
  List<Course> findAll();

  Optional<Course> findById(long id);

  void save(Course course);

  void delete(long id);
}
