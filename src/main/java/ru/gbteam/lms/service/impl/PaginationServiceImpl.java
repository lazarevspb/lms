package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.service.PaginationService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class PaginationServiceImpl implements PaginationService {

    @Override
    public Page<?> findPaginated(Optional<Integer> page, Optional<Integer> size, List<?> allEntities) {
        Integer DEFAULT_PAGE = 1;
        Integer DEFAULT_PAGE_SIZE = 5;

        int currentPage = page.orElse(DEFAULT_PAGE) - 1;
        int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

        int itemCount = currentPage * pageSize;
        List<?> resultList;

        if (allEntities.size() < itemCount) {
            resultList = Collections.emptyList();
        } else {
            int toIndex = Math.min(itemCount + pageSize, allEntities.size());
            resultList = allEntities.subList(itemCount, toIndex);
        }

        return new PageImpl<>(resultList, PageRequest.of(currentPage, pageSize), allEntities.size());
    }

    @Override
    public List<Integer> getLessonPageNumbers(Optional<Integer> page, Optional<Integer> size, List<?> allEntities) {
        int totalPages = findPaginated(page, size, allEntities).getTotalPages();
        if (totalPages > 0) {
            return IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
