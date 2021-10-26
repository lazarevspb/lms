package ru.gbteam.lms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.repository.LessonRepository;
import ru.gbteam.lms.service.LessonService;

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

    @Override
    public List<Lesson> findLessonsByModuleIdAndTitleLike(Long id, String title) {
        return lessonRepository.findAllByModuleIdAndTitleContainingIgnoreCase(id, title);
    }
}
