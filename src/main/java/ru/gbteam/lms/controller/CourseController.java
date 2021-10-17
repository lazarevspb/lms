package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.service.facade.CourseServiceFacadeImpl;

import javax.validation.Valid;
import ru.gbteam.lms.service.CourseServiceFacade;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseServiceFacade courseServiceFacade;
    private final Integer DEFAULT_PAGE = 1;
    private final Integer DEFAULT_PAGE_SIZE = 5;

    @DeleteMapping("/{courseId}/unassign/{userId}")
    public String unAssignUser(@PathVariable("courseId") Long courseId,
                               @PathVariable("userId") Long userId) {
        courseServiceFacade.unAssignUser(courseId, userId);
        return String.format("redirect:/course/%d", courseId);
    }

    @GetMapping("/{courseId}/assign")
    public String assignCourse(Model model, @PathVariable String courseId) {
        model.addAttribute("users", courseServiceFacade.findAllUsers()); // TODO: 04.10.2021 filtering user availability add
        return "assign";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUser(@PathVariable Long courseId, Long userId) {
        courseServiceFacade.assignUser(courseId, userId);
        return "redirect:/course";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseServiceFacade.deleteCourse(id);
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @PostMapping("/save")
    public String saveCourse(@Valid @ModelAttribute("course") CourseDTO courseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course_form";
        }
        courseServiceFacadeImpl.saveCourse(courseDto);
        return "redirect:/course";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model, @PathVariable("id") Long id) {
        final Course course = courseServiceFacade.findCourseById(id);
        model.addAttribute("modules", courseServiceFacade.findAllModulesByCourseId(course.getId()));
        model.addAttribute("course", course);
        model.addAttribute("users", course.getUsers());
        return "course_form";
    }

    @GetMapping
    public String courseTable(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam(name = "title", required = false) String title) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        Page<Course> coursePage = courseServiceFacade.findPaginated(PageRequest.of(currentPage - 1, pageSize), title);

        Page<Course> coursePage = courseServiceFacadeImpl.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("coursePage", coursePage);

        int totalPages = coursePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("activePage", "courses");
        return "course_table";
    }
}
