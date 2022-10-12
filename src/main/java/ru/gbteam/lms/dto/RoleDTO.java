package ru.gbteam.lms.dto;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String name;
}
