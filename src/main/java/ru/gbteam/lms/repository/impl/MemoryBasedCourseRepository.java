package ru.gbteam.lms.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.repository.CourseRepository;

@Repository
public class MemoryBasedCourseRepository implements CourseRepository {

  private final Map<Long, Course> courseMap = new ConcurrentHashMap<>();
  private final AtomicLong identity = new AtomicLong();

  @PostConstruct
  public void init() {
    save(new Course(null, "Автор 1", "Титл 1"));
    save(new Course(null, "Автор 2", "Титл 2"));
    save(new Course(null, "Автор 3", "Титл 3"));
  }

  @Override
  public List<Course> findAll() {
    return new ArrayList<>(courseMap.values());
  }

  @Override
  public Optional<Course> findById(long id) {
    return Optional.ofNullable(courseMap.get(id));
  }

  @Override
  public void save(Course course) {
    if (course.getId() == null) {
      course.setId(identity.incrementAndGet());
    }
    courseMap.put(course.getId(), course);
  }

  @Override
  public void delete(long id) {
    courseMap.remove(id);
  }
}
