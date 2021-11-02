package ru.gbteam.lms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.gbteam.lms.controller.LessonController;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.service.LessonServiceFacade;


@Controller
@RequiredArgsConstructor
public class LessonControllerImpl implements LessonController {
    private final LessonServiceFacade lessonServiceFacade;

    @Override
    public String getLessonById(Long lessonId, Model model) {
        model.addAttribute("lesson", lessonServiceFacade.mapLessonToDto(lessonId));
        return "lesson_form";
    }

    @Override
    public String newLesson(Long moduleId, Model model) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setModuleId(moduleId);
        model.addAttribute("lesson", lessonDTO);
        return "lesson_form";
    }

    @Override
    public String saveLesson(LessonDTO lessonDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        }
        lessonServiceFacade.saveLesson(lessonDTO);
        return "redirect:/module/" + lessonDTO.getModuleId();
    }

    @Override
    public String deleteLesson(Long id) {
        Long moduleId = lessonServiceFacade.findLessonById(id).getModule().getId();
        lessonServiceFacade.deleteLesson(id);
        return "redirect:/module/" + moduleId;
    }
}
