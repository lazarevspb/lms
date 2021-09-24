package ru.gbteam.lms.service;

import java.util.List;
import ru.gbteam.lms.model.Course;

public interface CourseService {
  List<Course> findAll();
}
