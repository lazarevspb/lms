package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.ModuleServiceFacade;

@RequiredArgsConstructor
@Service
public class ModuleServiceFacadeImpl implements ModuleServiceFacade {

    private final CourseService courseService;
    private final ModuleService moduleService;


    @Override
    public Course findCourseById(Long id) {
        return courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
    }

    @Override
    public Module findModuleById(Long id) {
        return moduleService.findById(id).orElseThrow(() -> new NotFoundException("Модуль", id));}

    @Override
    public void saveModule(Module module) {
        moduleService.save(module);
    }

    @Override
    public void deleteModel(Long id) {
        moduleService.delete(id);
    }

}
