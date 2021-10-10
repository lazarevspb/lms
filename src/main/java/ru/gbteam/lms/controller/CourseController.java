package ru.gbteam.lms.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final ModuleService moduleService;
    private final UserService userService;

    private final Integer DEFAULT_PAGE = 1;
    private final Integer DEFAULT_PAGE_SIZE = 5;

    public CourseController(CourseService courseService, ModuleService moduleService, UserService userService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
        this.userService = userService;
    }

    @DeleteMapping("/{courseId}/unassign/{userId}")
    public String unAssignUser(@PathVariable("courseId") Long courseId,
                               @PathVariable("userId") Long userId) {
        User user = userService.findById(userId)
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
        User user = userService.findById(userId)
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
    public String courseForm(Model model, @PathVariable("id") Long id) {
        final Course course = courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
        model.addAttribute("modules", moduleService.findAllByCourseId(course.getId()));
        model.addAttribute("course", course);
        model.addAttribute("users", course.getUsers());
        return "course_form";
    }

    @GetMapping
    public String courseTable(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        Page<Course> coursePage = courseService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("coursePage", coursePage);

        int totalPages = coursePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "course_table";
    }
}
