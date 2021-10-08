package ru.gbteam.lms.service.service_facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.service_interface.CourseService;
import ru.gbteam.lms.service.service_interface.ModuleService;
import ru.gbteam.lms.service.service_interface.UserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModuleFacadeService {

    private final CourseService courseService;
    private final ModuleService moduleService;


    public Course findCourseById(Long id) {
        return courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
    }

    public Module findModuleById(Long id) {
        return moduleService.findById(id).orElseThrow(() -> new NotFoundException("Модуль", id));}

}
