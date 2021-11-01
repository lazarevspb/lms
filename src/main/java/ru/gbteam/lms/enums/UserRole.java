package ru.gbteam.lms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_OWNER(1L),
    ROLE_ADMIN(2L),
    ROLE_TUTOR(3L),
    ROLE_STUDENT(4L);

    private final Long id;

    public Long getId() {
        return id;
    }
}
