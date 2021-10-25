package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.LessonService;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.ModuleServiceFacade;
import ru.gbteam.lms.service.PaginationService;

import java.util.List;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ModuleServiceFacadeImpl implements ModuleServiceFacade {

    private final CourseService courseService;
    private final ModuleService moduleService;
    private final LessonService lessonService;
    private final PaginationService paginationService;

    @Override
    public Course findCourseById(Long id) {
        return courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
    }

    @Override
    public Module findModuleById(Long id) {
        return moduleService.findById(id).orElseThrow(() -> new NotFoundException("Модуль", id));
    }

    @Override
    public void saveModule(Module module) {
        moduleService.save(module);
    }

    @Override
    public void deleteModule(Long id) {
        moduleService.delete(id);
    }

    @Override
    public Page<Lesson> findLessonPaginated(Long module_id, Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return (Page<Lesson>) paginationService.findPaginated(page, size, lessonService.findLessonsByModuleIdAndTitleLike(module_id,(titlePrefix == null ? "" : titlePrefix)));
    }

    @Override
    public List<Integer> getLessonPageNumbers(Long module_id, Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return paginationService.getLessonPageNumbers(page, size, lessonService.findLessonsByModuleIdAndTitleLike(module_id,(titlePrefix == null ? "" : titlePrefix)));
    }

    @Override
    public List<Lesson> findAllLessonsByModuleId(Long id) {
        return lessonService.findAllByModuleId(id);
    }

}
