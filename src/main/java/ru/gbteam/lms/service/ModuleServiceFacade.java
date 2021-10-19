package ru.gbteam.lms.service;

import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;

public interface ModuleServiceFacade {

    Course findCourseById(Long id);

    Module findModuleById(Long id);

    void saveModule(Module module);

    void deleteModel(Long id);
}
