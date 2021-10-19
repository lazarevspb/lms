package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.repository.LessonRepository;
import ru.gbteam.lms.service.LessonService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> findAllByModuleId(Long id) {
        return lessonRepository.findAllByModuleId(id);
    }

    public Page<Lesson> findPaginated(Long moduleId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        int itemCount = currentPage * pageSize;

        List<Lesson> allLessons = findAllByModuleId(moduleId);
        List<Lesson> resultListLessons;

        if (allLessons.size() < itemCount) {
            resultListLessons = Collections.emptyList();
        } else {
            int toIndex = Math.min(itemCount + pageSize, allLessons.size());
            resultListLessons = allLessons.subList(itemCount, toIndex);
        }

        return new PageImpl<>(resultListLessons, PageRequest.of(currentPage, pageSize), allLessons.size());
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }
}
