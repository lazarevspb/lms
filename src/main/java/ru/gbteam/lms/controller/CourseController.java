package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbteam.lms.exception.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.service.ModuleServiceFacade;
import ru.gbteam.lms.service.facade.CourseServiceFacadeImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseServiceFacadeImpl courseServiceFacadeImpl;
    private final ModuleServiceFacade moduleServiceFacade;


    @DeleteMapping("/{courseId}/unassign/{userId}")
    public String unAssignUser(@PathVariable("courseId") Long courseId,
                               @PathVariable("userId") Long userId) {
        courseServiceFacadeImpl.unAssignUser(courseId, userId);
        return String.format("redirect:/course/%d", courseId);
    }

    @GetMapping("/{courseId}/assign")
    public String assignCourse(Model model, @PathVariable String courseId) {
        model.addAttribute("users", courseServiceFacadeImpl.findAllUsers()); // TODO: 04.10.2021 filtering user availability add
        return "assign";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUser(@PathVariable Long courseId, Long userId) {
        courseServiceFacadeImpl.assignUser(courseId, userId);
        return "redirect:/course";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseServiceFacadeImpl.deleteCourse(id);
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @PostMapping("/save")
    public String saveCourse(Course course) {
        courseServiceFacadeImpl.saveCourse(course);
        return "redirect:/course";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model,
                             @PathVariable("id") Long id,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        final Course course = courseServiceFacadeImpl.findCourseById(id);
        model.addAttribute("modulePage", moduleServiceFacade.findPaginated(id, page, size));

        List<Integer> pageNumbers = moduleServiceFacade.getPageNumbers(id, page, size, model);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageNumbers", pageNumbers);
        }
        //model.addAttribute("modules", courseServiceFacadeImpl.findAllModulesByCourseId(course.getId()));
        model.addAttribute("course", course);
        model.addAttribute("users", course.getUsers());
        return "course_form";
    }

    @GetMapping
    public String courseTable(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {

        model.addAttribute("coursePage", courseServiceFacadeImpl.findPaginated(page, size));

        List<Integer> pageNumbers = courseServiceFacadeImpl.getPageNumbers(page, size, model);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "course_table";
    }
}
