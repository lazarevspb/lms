package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PaginationService {

    Page<?> findPaginated(Optional<Integer> page, Optional<Integer> size, List<?> allEntities);
    List<Integer> getLessonPageNumbers(Optional<Integer> page, Optional<Integer> size , List<?> allEntities);
}
