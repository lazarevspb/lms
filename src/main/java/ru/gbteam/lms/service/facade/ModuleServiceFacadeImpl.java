package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.ModuleServiceFacade;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class ModuleServiceFacadeImpl implements ModuleServiceFacade {

    private final Integer DEFAULT_PAGE = 1;
    private final Integer DEFAULT_PAGE_SIZE = 5;

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

    @Override
    public Page<Module> findPaginated(Long course_id, Optional<Integer> page, Optional<Integer> size){
        int currentPage = page.orElse(DEFAULT_PAGE);
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);
        return moduleService.findPaginated(course_id, PageRequest.of(currentPage - 1, pageSize));
    }

    @Override
    public List<Integer> getPageNumbers(Long course_id, Optional<Integer> page, Optional<Integer> size, Model model){
        int totalPages = findPaginated(course_id, page,  size).getTotalPages();
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
