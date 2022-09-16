package ru.gbteam.lms.converter;

import org.springframework.core.convert.converter.Converter;
import ru.gbteam.lms.dto.RoleDTO;
import ru.gbteam.lms.model.Role;
import ru.gbteam.lms.repository.RoleRepository;


public class RoleDtoByStringConverter implements Converter<String, RoleDTO> {

    private final RoleRepository roleRepository;

    public RoleDtoByStringConverter(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO convert(String id) {
        Long roleId = Long.parseLong(id);
        Role role = roleRepository.findById(roleId).get();
        return new RoleDTO(role.getId(), role.getName()); // TODO: 16.09.2022 в маппер
    }
}
