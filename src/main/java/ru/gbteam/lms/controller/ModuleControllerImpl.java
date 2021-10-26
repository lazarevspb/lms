package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import ru.gbteam.lms.controller.impl.ModuleController;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.ModuleServiceFacade;
import ru.gbteam.lms.model.Course;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ModuleControllerImpl implements ModuleController {

    private final ModuleServiceFacade moduleServiceFacade;

    public String newModuleForm(Model model, Long course_id) {
        final Course course = moduleServiceFacade.findCourseById(course_id);

        model.addAttribute("module", new Module(course));
        return "module_form";
    }

    public String saveModule(Module module) {
        moduleServiceFacade.saveModule(module);
        return String.format("redirect:/course/%d", module.getCourse().getId());
    }

    public String moduleForm(Model model, Long id, Optional<Integer> page, Optional<Integer> size, String titlePrefix) {

        model.addAttribute("lessonPage", moduleServiceFacade.findLessonPaginated(id, page, size, titlePrefix));
        List<Integer> pageNumbers = moduleServiceFacade.getLessonPageNumbers(id, page, size, titlePrefix);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageLessonNumbers", pageNumbers);
        }

        model.addAttribute("module",
                moduleServiceFacade.findModuleById(id));
        return "module_form";
    }

    public String deleteModel(Long id) {
        Long course_id = moduleServiceFacade.findModuleById(id).getCourse().getId();
        moduleServiceFacade.deleteModule(id);
        return String.format("redirect:/course/%d", course_id);
    }
}
