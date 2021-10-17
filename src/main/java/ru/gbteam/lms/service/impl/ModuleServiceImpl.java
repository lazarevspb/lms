package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.repository.ModuleRepository;
import ru.gbteam.lms.service.ModuleService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public List<Module> findAllByCourseId(Long id) {
        return moduleRepository.findAllByCourseId(id);
    }

    @Override
    public Page<Module> findPaginated(Long course_id, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        int itemCount = currentPage * pageSize;

        List<Module> allModules = findAllByCourseId(course_id);
        List<Module> resultListModules;

        if (allModules.size() < itemCount) {
            resultListModules = Collections.emptyList();
        } else {
            int toIndex = Math.min(itemCount + pageSize, allModules.size());
            resultListModules = allModules.subList(itemCount, toIndex);
        }

        return new PageImpl<>(resultListModules, PageRequest.of(currentPage, pageSize), allModules.size());
    }


    @Override
    public Optional<Module> findById(Long id) {
        return moduleRepository.findById(id);
    }

    @Override
    public void save(Module module) {
        moduleRepository.save(module);
    }

    @Override
    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }
}
