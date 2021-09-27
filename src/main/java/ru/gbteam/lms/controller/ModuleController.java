package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;

@Controller
@RequestMapping("/course/{course_id}/module")
@RequiredArgsConstructor

public class ModuleController {
    private final ModuleService moduleService;
    private final CourseService courseService;

    @GetMapping("/new")
    public String newModuleForm(Model model, @PathVariable("course_id") Long course_id) {
        var course = courseService.findById(course_id);
        var module = new Module();
        course.ifPresent(module::setCourse);
        model.addAttribute("module", module);
        return "module_form";
    }

    @PostMapping
    public String saveModule(Module module, @PathVariable("course_id") Long course_id) {
        moduleService.save(module);
        return "redirect:/course/" + course_id + "/module";
    }

    @GetMapping("/{id}")
    public String moduleForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("module",
                moduleService.findById(id).orElseThrow(() -> new NotFoundException("Модуль", id)));
        return "module_form";
    }

    @GetMapping
    public String moduleTable(Model model, @PathVariable("course_id") Long course_id) {
        model.addAttribute("modules", moduleService.findAllByCourseId(course_id));
        return "module_table";
    }

    @DeleteMapping("/{id}")
    public String deleteModel(@PathVariable("id") Long id) {
        moduleService.delete(id);
        return "redirect:/course/{course_id}/module";
    }
}
