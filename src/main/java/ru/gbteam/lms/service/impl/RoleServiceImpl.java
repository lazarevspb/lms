package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.repository.RoleRepository;
import ru.gbteam.lms.service.RoleService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
