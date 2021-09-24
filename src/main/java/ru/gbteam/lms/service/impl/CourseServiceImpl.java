package ru.gbteam.lms.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public List<Course> findAll() {
    return courseRepository.findAll();
  }
}
