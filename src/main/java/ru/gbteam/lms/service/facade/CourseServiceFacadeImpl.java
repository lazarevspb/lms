package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.CourseServiceFacade;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.UserService;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseServiceFacadeImpl implements CourseServiceFacade {

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
    public Page<Course> findPaginated(Pageable pageable){
        return courseService.findPaginated(pageable);
    }

    public List<Course> findCoursesByTitleLike(String search) {
        return courseService.findCoursesByTitleLike(search);
    }
}
