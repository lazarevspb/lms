package ru.gbteam.lms.service.impl;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> findCoursesByTitleOrAuthorLike(String title) {
        return courseRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(title, title);
    }
}
