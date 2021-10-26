package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.repository.ModuleRepository;
import ru.gbteam.lms.service.ModuleService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public List<Module> findAllByCourseId(Long id) {
        return moduleRepository.findAllByCourseId(id);
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

    @Override
    public List<Module> findModulesByCourseIdAndTitleLike(Long id, String title) {
        return moduleRepository.findAllByCourseIdAndTitleContainingIgnoreCase(id, title);
    }
}
