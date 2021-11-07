package ru.gbteam.lms.service;

import ru.gbteam.lms.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    Optional<Role> findById(Long id);
}
