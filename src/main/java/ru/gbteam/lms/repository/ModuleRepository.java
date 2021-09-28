package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbteam.lms.model.Module;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByCourseId(Long id);
}
