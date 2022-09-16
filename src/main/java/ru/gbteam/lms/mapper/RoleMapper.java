package ru.gbteam.lms.mapper;

import ru.gbteam.lms.dto.RoleDTO;
import ru.gbteam.lms.model.Role;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
public class RoleMapper {

    public static Set<RoleDTO> roleMapper(Set<Role> roles) {
        return roles.stream().map(role -> new RoleDTO(role.getId(), role.getName())).collect(Collectors.toSet());
    }

    public static Set<Role> roleDtoMapper(Set<RoleDTO> roleDTOS) {
        return roleDTOS.stream().map(role -> new Role(role.getId(), role.getName(), Collections.EMPTY_SET)).collect(Collectors.toSet());
    }
}
