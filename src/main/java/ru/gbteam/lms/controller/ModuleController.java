package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.ModuleServiceFacade;
import ru.gbteam.lms.model.Course;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/module")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleServiceFacade moduleServiceFacade;

    @GetMapping("/new")
    public String newModuleForm(Model model, @RequestParam("course_id") Long course_id) {
        final Course course = moduleServiceFacade.findCourseById(course_id);

        model.addAttribute("module", new Module(course));
        return "module_form";
    }

    @PostMapping("/save")
    public String saveModule(Module module) {
        moduleServiceFacade.saveModule(module);
        return String.format("redirect:/course/%d", module.getCourse().getId());
    }

    @GetMapping("/{id}")
    public String moduleForm(Model model,
                             @PathVariable("id") Long id,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {

        model.addAttribute("lessonPage", moduleServiceFacade.findLessonPaginated(id, page, size));
        List<Integer> pageNumbers = moduleServiceFacade.getLessonPageNumbers(id, page, size);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageLessonNumbers", pageNumbers);
        }

        model.addAttribute("module",
                moduleServiceFacade.findModuleById(id));
        return "module_form";
    }

    @DeleteMapping("/{id}")
    public String deleteModel(@PathVariable("id") Long id) {

        Long course_id = moduleServiceFacade.findModuleById(id)
                .getCourse().getId();
        moduleServiceFacade.deleteModel(id);
        return String.format("redirect:/course/%d", course_id);
    }

}
