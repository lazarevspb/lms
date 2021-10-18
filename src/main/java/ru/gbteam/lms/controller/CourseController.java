package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.service.CourseServiceFacade;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseServiceFacade courseServiceFacade;

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
    public String saveCourse(Course course) {
        courseServiceFacade.saveCourse(course);
        return "redirect:/course";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model,
                             @PathVariable("id") Long id,
                             @RequestParam("modulePage") Optional<Integer> modulePage,
                             @RequestParam("moduleSize") Optional<Integer> moduleSize,
                             @RequestParam("userPage") Optional<Integer> userPage,
                             @RequestParam("userSize") Optional<Integer> userSize) {
        final Course course = courseServiceFacade.findCourseById(id);

        model.addAttribute("modulePage", courseServiceFacade.findModulePaginated(id, modulePage, moduleSize));
        List<Integer> pageNumbers = courseServiceFacade.getModulePageNumbers(id, modulePage, moduleSize);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageModelNumbers", pageNumbers);
        }

        model.addAttribute("userPage", courseServiceFacade.findUserPaginated(userPage, userSize));
        List<Integer> pageUserNumbers = courseServiceFacade.getUserPageNumbers(userPage, userSize, model);
        if (!CollectionUtils.isEmpty(pageUserNumbers)) {
            model.addAttribute("pageUserNumbers", pageUserNumbers);
        }

        model.addAttribute("course", course);
        return "course_form";
    }

    @GetMapping
    public String courseTable(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              @RequestParam(name = "title", required = false) String title) {

        model.addAttribute("coursePage", courseServiceFacade.findPaginated(page, size, title));
        List<Integer> pageNumbers = courseServiceFacade.getPageNumbers(page, size, title);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "course_table";
    }
}
