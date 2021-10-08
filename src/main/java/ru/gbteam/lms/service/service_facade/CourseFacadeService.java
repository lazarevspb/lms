package ru.gbteam.lms.service.service_facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.service.service_interface.CourseService;
import ru.gbteam.lms.service.service_interface.ModuleService;
import ru.gbteam.lms.service.service_interface.UserService;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class CourseFacadeService {

    private final CourseService courseService;
    private final ModuleService moduleService;
    private final UserService userService;


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

    public List<User> findAllUsers() {
        return userService.findAll();
    }

    public Course findCourseById(Long id) {
        return courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
    }
    public List<Module> findAllModulesByCourseId(Long id) {
        return moduleService.findAllByCourseId(id);
    }
}
