package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.RoleService;
import ru.gbteam.lms.service.UserService;
import ru.gbteam.lms.service.impl.UserDtoServiceImpl;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ModuleService moduleService;
    private final UserService userService;
    private final UserDtoServiceImpl userDtoService;
    private final RoleService roleService;

    @DeleteMapping("/{courseId}/unassign/{userId}")
    public String unAssignUser(@PathVariable("courseId") Long courseId,
                               @PathVariable("userId") Long userId) {
        User user = userService.findById(userId) // TODO: 06.10.2021 Перенести логику в CourseControllerFacade
                .orElseThrow(() -> new NotFoundException("Пользователь", userId));
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс", courseId));
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        courseService.save(course);
        return String.format("redirect:/course/%d", courseId);
    }

    @GetMapping("/{courseId}/assign")
    public String assignCourse(Model model, @PathVariable String courseId) {
        model.addAttribute("users", userService.findAll()); // TODO: 04.10.2021 filtering user availability add
        return "assign";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUser(@PathVariable Long courseId, Long userId) {
        User user = userService.findById(userId) // TODO: 06.10.2021 Перенести логику в CourseControllerFacade
                .orElseThrow(() -> new NotFoundException("Пользователь", userId));
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс", courseId));
        course.getUsers().add(user);
        user.getCourses().add(course);
        courseService.save(course);
        return "redirect:/course";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @PostMapping("/save")
    public String saveCourse(Course course) {
        courseService.save(course);
        return "redirect:/course";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model, @PathVariable("id") Long id) {// TODO: 06.10.2021 Перенести логику в CourseControllerFacade
        final Course course = courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
        model.addAttribute("modules", moduleService.findAllByCourseId(course.getId()));
        model.addAttribute("course", course);
        model.addAttribute("users", course.getUsers());
        return "course_form";
    }

    @GetMapping
    public String courseTable(Model model) {
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("activePage", "courses");
        return "course_table";
    }
}
