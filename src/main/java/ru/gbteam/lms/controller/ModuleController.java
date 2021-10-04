package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;

@Controller
@RequestMapping("/module")
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    private final CourseService courseService;

    @GetMapping("/new")
    public String newModuleForm(Model model, @RequestParam("course_id") Long course_id) {
        var course = courseService.findById(course_id)
                .orElseThrow(() -> new NotFoundException("Курс", course_id));
        model.addAttribute("module", new Module(course));
        return "module_form";
    }

    @PostMapping("/save")
    public String saveModule(Module module) {
        moduleService.save(module);
        return String.format("redirect:/course/%d", module.getCourse().getId());
    }

    @GetMapping("/{id}")
    public String moduleForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("module",
                moduleService.findById(id).orElseThrow(() -> new NotFoundException("Модуль", id)));
        return "module_form";
    }

    @DeleteMapping("/{id}")
    public String deleteModel(@PathVariable("id") Long id) {
        Long course_id = moduleService
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Модуль", id))
                .getCourse().getId();
        moduleService.delete(id);
        return String.format("redirect:/course/%d", course_id);
    }
}
