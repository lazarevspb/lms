package ru.gbteam.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gbteam.lms.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  public String courseTable(Model model) {
    model.addAttribute("courses", courseService.findAll());
    return "course_table";
  }

}
