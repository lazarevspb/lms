package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbteam.lms.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
