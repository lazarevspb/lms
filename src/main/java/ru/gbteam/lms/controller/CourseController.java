package ru.gbteam.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("/{id}")
  public String courseForm(Model model, @PathVariable("id") Long id) {
    model.addAttribute("course",
        courseService.findById(id).orElseThrow(() -> new NotFoundException("Course not found")));
    return "course_form";
  }

  @GetMapping
  public String courseTable(Model model) {
    model.addAttribute("courses", courseService.findAll());
    return "course_table";
  }

}
