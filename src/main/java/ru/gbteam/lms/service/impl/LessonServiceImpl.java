package ru.gbteam.lms.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.repository.LessonRepository;
import ru.gbteam.lms.service.LessonService;
import ru.gbteam.lms.service.MapperService;

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
