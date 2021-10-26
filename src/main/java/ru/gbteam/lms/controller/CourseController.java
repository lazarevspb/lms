package ru.gbteam.lms.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbteam.lms.dto.CourseDTO;

import java.util.Optional;
import javax.validation.Valid;

@RequestMapping("/course")
public interface CourseController {
    @DeleteMapping("/{courseId}/unassign/{userId}")
    String unAssignUser(@PathVariable("courseId") Long courseId,
                               @PathVariable("userId") Long userId);

    @GetMapping("/{courseId}/assign")
    String assignCourse(Model model, @PathVariable String courseId);

    @PostMapping("/{courseId}/assign")
    String assignUser(@PathVariable Long courseId, Long userId);

    @DeleteMapping("/{id}")
    String deleteCourse(@PathVariable("id") Long id);

    @GetMapping("/new")
    String courseForm(Model model);

    @PostMapping("/save")
    String saveCourse(@Valid @ModelAttribute("course") CourseDTO courseDto, BindingResult bindingResult);

    @GetMapping("/{id}")
    String courseForm(Model model,
                             @PathVariable("id") Long id,
                             @RequestParam("modulePage") Optional<Integer> modulePage,
                             @RequestParam("moduleSize") Optional<Integer> moduleSize,
                             @RequestParam("userPage") Optional<Integer> userPage,
                             @RequestParam("userSize") Optional<Integer> userSize);
    @GetMapping
    String courseTable(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam(name = "title", required = false) String title);
}
