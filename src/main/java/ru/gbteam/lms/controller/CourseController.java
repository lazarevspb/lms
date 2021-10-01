package ru.gbteam.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final ModuleService moduleService;

    public CourseController(CourseService courseService, ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
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
        model.addAttribute("course",
                courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id)));
        return "course_form";
    }

    @GetMapping
    public String courseTable(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "course_table";
    }

    @GetMapping("{course_id}/module")
    public String moduleTable(Model model, @PathVariable("course_id") Long course_id) {
        model.addAttribute("modules", moduleService.findAllByCourseId(course_id));
        return "module_table";
    }
}
