package ru.gbteam.lms.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Course> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        int itemCount = currentPage * pageSize;

        List<Course> allCourses = findAll();
        List<Course> resultListCourses;

        if (allCourses.size() < itemCount) {
            resultListCourses = Collections.emptyList();
        } else {
            int toIndex = Math.min(itemCount + pageSize, allCourses.size());
            resultListCourses = allCourses.subList(itemCount, toIndex);
        }

        return new PageImpl<>(resultListCourses, PageRequest.of(currentPage, pageSize), allCourses.size());
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
}
