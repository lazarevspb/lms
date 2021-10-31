package ru.gbteam.lms.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbteam.lms.dto.LessonDTO;

import javax.validation.Valid;

@RequestMapping("/lesson")

public interface LessonController {
    @GetMapping("/{lesson_id}")
    String getLessonById(@PathVariable("lesson_id") Long lessonId, Model model);

    @GetMapping("/new")
    String newLesson(@RequestParam(name = "module_id") Long moduleId, Model model);

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    String saveLesson(@Valid @ModelAttribute("lesson") LessonDTO lessonDTO, BindingResult bindingResult);

    @DeleteMapping("/{lesson_id}")
    @Secured("ROLE_ADMIN")
    String deleteLesson(@PathVariable(name = "lesson_id") Long id);
}
