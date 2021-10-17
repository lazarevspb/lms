package ru.gbteam.lms.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.repository.LessonRepository;
import ru.gbteam.lms.service.LessonService;
import ru.gbteam.lms.service.MapperService;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    private final MapperService mapperService;

    public List<Lesson> findAllByModuleId(Long id) {
        return lessonRepository.findAllByModuleId(id);
    }

    public Page<Lesson> findPaginated(Long moduleId, Pageable pageable){
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

    public LessonDTO findLessonById(Long id) {
        return lessonRepository.findById(id).map(mapperService::toDTO).orElse(new LessonDTO());
    }

    public void save(LessonDTO lessonDTO) {
        Lesson lesson = mapperService.fromDTO(lessonDTO);
        lessonRepository.save(lesson);
    }

    public Long delete(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new NotFoundException("Урок", id));
        Long moduleId = lesson.getModule().getId();
        lessonRepository.delete(lesson);
        return moduleId;
    }
}
