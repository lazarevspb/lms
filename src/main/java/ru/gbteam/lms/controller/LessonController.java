package ru.gbteam.lms.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.service.CourseService;

@Controller
@RequestMapping("/lessons")
@AllArgsConstructor
public class LessonController {

//    private final LessonService lessonService;

    @GetMapping("/{course_id}/{module_id}")
    public String getAllLessonsInModule(@PathVariable("course_id") Long courseId, @PathVariable("module_id") Long moduleId, Model model) {
//        model.addAttribute("lessons", lessonRepository.getLessonsInModule(moduleId));
        return "lessons";
    }

    @GetMapping("/{course_id}/{module_id}/{lesson_id}")
    public String getLessonById(@PathVariable("course_id") Long courseId, @PathVariable("module_id") Long moduleId, @PathVariable("lesson_id") Long lessonId, Model model) {
//        model.addAttribute("lesson", lessonRepository.getLessonById(id));
        return "lesson_info";
    }

    @GetMapping("/{course_id}/{module_id}/new")
    public String newLesson(@PathVariable("course_id") Long courseId, @PathVariable("module_id") Long moduleId, Model model) {
//        model.addAttribute("lessons", new LessonDTO());
        return "lesson_form";
    }

//    @PostMapping("/{course_id}/{module_id}")
//    public String saveLesson(@PathVariable("course_id") Long courseId, @PathVariable("module_id") Long moduleId, LessonDTO lessonDTO) {
//        lessonService.save(lessonDTO);
//        return "redirect:/lessons";
//    }

    @DeleteMapping("/{lesson_id}")
    public String deleteLesson(@PathVariable Long id) {
//        lessonService.delete(id);
        return "redirect:/lessons";
    }



}
