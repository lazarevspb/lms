package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.service.LessonServiceFacade;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonServiceFacade lessonServiceFacade;

    @GetMapping("/{lesson_id}")
    public String getLessonById(@PathVariable("lesson_id") Long lessonId, Model model) {
        model.addAttribute("lesson", lessonServiceFacade.mapLessonToDto(lessonId));
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
    public String saveLesson(@Valid @ModelAttribute("lesson") LessonDTO lessonDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        }
        lessonServiceFacade.saveLesson(lessonDTO);
        return "redirect:/module/" + lessonDTO.getModuleId();
    }

    @DeleteMapping("/{lesson_id}")
    public String deleteLesson(@PathVariable(name = "lesson_id") Long id) {
        Long moduleId = lessonServiceFacade.findLessonById(id).getModule().getId();
        lessonServiceFacade.deleteLesson(id);
        return "redirect:/module/" + moduleId;
    }
}
