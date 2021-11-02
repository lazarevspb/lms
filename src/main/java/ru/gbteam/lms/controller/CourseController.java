package ru.gbteam.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gbteam.lms.dto.CourseDTO;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/course")
public interface CourseController {
    @PostMapping("/courseImage")
    String updateCourseImage(@RequestParam Long id,
                                    @RequestParam("courseImage")
                                            MultipartFile courseImage,
                                    HttpServletRequest request);

    @GetMapping("/courseImage/{courseId}")
    @ResponseBody
    ResponseEntity<byte[]> courseImage(@PathVariable Long courseId);

    @DeleteMapping("/{courseId}/unassign/{userId}")
    String unAssignUser(@PathVariable("courseId") Long courseId,
                               @PathVariable("userId") Long userId);

    @GetMapping("/{courseId}/assign")
    String assignCourse(Model model, @PathVariable String courseId);

    @PostMapping("/{courseId}/assign")
    String assignUser(@PathVariable Long courseId, Long userId);

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    String deleteCourse(@PathVariable("id") Long id);

    @GetMapping("/new")
    @Secured("ROLE_ADMIN")
    String courseForm(Model model);

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    String saveCourse(@Valid @ModelAttribute("course") CourseDTO courseDto, BindingResult bindingResult);

    @GetMapping("/{id}")
    String courseForm(Model model,
                             @PathVariable("id") Long id,
                             @RequestParam("modulePage") Optional<Integer> modulePage,
                             @RequestParam("moduleSize") Optional<Integer> moduleSize,
                             @RequestParam(name = "title", required = false) String title,
                             @RequestParam("userPage") Optional<Integer> userPage,
                             @RequestParam("userSize") Optional<Integer> userSize);
    @GetMapping
    String courseTable(Model model,
                              @RequestParam(name="page", defaultValue = "1", required = false) Optional<Integer> page,
                              @RequestParam(name="size", defaultValue = "10", required = false) Optional<Integer> size,
                              @RequestParam(name = "title", required = false) String title);
}
