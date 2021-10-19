package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.service.LessonService;
import ru.gbteam.lms.service.LessonServiceFacade;
import ru.gbteam.lms.service.MapperService;

@RequiredArgsConstructor
@Service
public class LessonServiceFacadeImpl implements LessonServiceFacade {
    private final LessonService lessonService;
    private final MapperService mapperService;

    @Override
    public LessonDTO mapLessonToDto(Long id) {
        return lessonService.findById(id).map(mapperService::toDTO).orElse(new LessonDTO());
    }

    @Override
    public void saveLesson(LessonDTO lessonDTO) {
        Lesson lesson = mapperService.fromDTO(lessonDTO);
        lessonService.save(lesson);
    }

    @Override
    public Lesson findLessonById(Long id) {
        return lessonService.findById(id).orElseThrow(() -> new NotFoundException("Урок", id));
    }

    @Override
    public void deleteLesson(Long id) {
        lessonService.deleteById(id);
    }
}
