package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import java.util.List;
import java.util.Optional;

public interface ModuleService {
    List<Module> findAllByCourseId(Long id);

    Optional<Module> findById(Long id);

    void save(Module module);

    void delete(Long id);
}
