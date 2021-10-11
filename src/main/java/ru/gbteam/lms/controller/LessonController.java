package ru.gbteam.lms.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.LessonService;

@Controller
@RequestMapping("/lesson")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;

//    @GetMapping("/{course_id}/{module_id}")
//    public String getAllLessonsInModule(@PathVariable("course_id") Long courseId, @PathVariable("module_id") Long moduleId, Model model) {
////        model.addAttribute("lessons", lessonRepository.getLessonsInModule(moduleId));
//        return "lessons";
//    }

    @GetMapping("/{lesson_id}")
    public String getLessonById(@PathVariable("lesson_id") Long lessonId, Model model) {
        model.addAttribute("lesson", lessonService.findLessonById(lessonId));
        return "lesson_form";
    }

    @GetMapping("/new")
    public String newLesson(@RequestParam(name = "module_id") Long moduleId, Model model) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setModuleId(moduleId);
        model.addAttribute("lesson", lessonDTO);
        return "lesson_form";
    }

    @PostMapping("/save")
    public String saveLesson(LessonDTO lessonDTO) {
        lessonService.save(lessonDTO);
        return "redirect:/module/" + lessonDTO.getModuleId();
    }

    @DeleteMapping("/{lesson_id}")
    public String deleteLesson(@PathVariable(name = "lesson_id") Long id) {
        Long moduleId = lessonService.delete(id);
        return "redirect:/module/" + moduleId;
    }

}
