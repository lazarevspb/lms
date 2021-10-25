package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceFacadeImpl implements CourseServiceFacade {
    private final CourseService courseService;
    private final ModuleService moduleService;
    private final UserService userService;
    private final MapperService mapperService;
    private final PaginationService paginationService;

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
    public void saveCourse(CourseDTO courseDTO) {
        Course c = mapperService.fromDTO(courseDTO);
        courseService.save(c);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseService.findAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Course> findPaginated(Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return (Page<Course>) paginationService.findPaginated(page, size,
                courseService.findCoursesByTitleOrAuthorLike(titlePrefix == null ? "" : titlePrefix));
    }

    @Override
    public List<Integer> getPageNumbers(Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return paginationService.getLessonPageNumbers(page, size,
                courseService.findCoursesByTitleOrAuthorLike(titlePrefix == null ? "" : titlePrefix));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Module> findModulePaginated(Long course_id, Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return (Page<Module>) paginationService.findPaginated(page, size,
                moduleService.findModulesByCourseIdAndTitleLike(course_id,(titlePrefix == null ? "" : titlePrefix)));
    }

    @Override
    public List<Integer> getModulePageNumbers(Long course_id, Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return paginationService.getLessonPageNumbers(page, size,
                moduleService.findModulesByCourseIdAndTitleLike(course_id,(titlePrefix == null ? "" : titlePrefix)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<User> findUserPaginated(Optional<Integer> page, Optional<Integer> size) {
        return (Page<User>) paginationService.findPaginated(page, size,
                userService.findAll());
    }

    @Override
    public List<Integer> getUserPageNumbers(Optional<Integer> page, Optional<Integer> size, Model model) {
        return paginationService.getLessonPageNumbers(page, size,
                userService.findAll());
    }
}
