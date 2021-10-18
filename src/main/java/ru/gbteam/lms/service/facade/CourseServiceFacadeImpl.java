package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.CourseServiceFacade;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class CourseServiceFacadeImpl implements CourseServiceFacade {
    private final Integer DEFAULT_PAGE = 1;
    private final Integer DEFAULT_PAGE_SIZE = 5;

    private final CourseService courseService;
    private final ModuleService moduleService;
    private final UserService userService;

    @Override
    @Transactional
    public void unAssignUser(Long courseId, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь", userId));
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс", courseId));
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        courseService.save(course);
    }

    @Override
    @Transactional
    public void assignUser(Long courseId, Long userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь", userId));
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс", courseId));
        course.getUsers().add(user);
        user.getCourses().add(course);
        courseService.save(course);
    }

    @Override
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @Override
    public Course findCourseById(Long id) {
        return courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
    }

    @Override
    public List<Module> findAllModulesByCourseId(Long id) {
        return moduleService.findAllByCourseId(id);
    }

    @Override
    public void deleteCourse(Long id) {
        courseService.delete(id);
    }

    @Override
    public void saveCourse(Course course) {
        courseService.save(course);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseService.findAll();
    }

    @Override
    public Page<Course> findPaginated(Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        return courseService.findPaginated(PageRequest.of(currentPage - 1, pageSize), titlePrefix);
    }

    @Override
    public List<Integer> getPageNumbers(Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        int totalPages = findPaginated(page, size, titlePrefix).getTotalPages();
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Page<Module> findModulePaginated(Long course_id, Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        return moduleService.findPaginated(course_id, PageRequest.of(currentPage - 1, pageSize));
    }

    @Override
    public List<Integer> getModulePageNumbers(Long course_id, Optional<Integer> page, Optional<Integer> size) {
        int totalPages = findModulePaginated(course_id, page, size).getTotalPages();
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Page<User> findUserPaginated(Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        return userService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
    }

    @Override
    public List<Integer> getUserPageNumbers(Optional<Integer> page, Optional<Integer> size, Model model) {
        int totalPages = findUserPaginated(page, size).getTotalPages();
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
