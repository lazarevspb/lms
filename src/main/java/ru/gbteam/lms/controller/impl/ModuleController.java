package ru.gbteam.lms.controller.impl;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbteam.lms.model.Module;

import java.util.Optional;

@RequestMapping("/module")
public interface ModuleController {
    @GetMapping("/new")
    String newModuleForm(Model model, @RequestParam("course_id") Long course_id);

    @PostMapping("/save")
    String saveModule(Module module);

    @GetMapping("/{id}")
    String moduleForm(Model model,
                             @PathVariable("id") Long id,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             @RequestParam(name = "title", required = false) String title);

    @DeleteMapping("/{id}")
    String deleteModel(@PathVariable("id") Long id);
}
