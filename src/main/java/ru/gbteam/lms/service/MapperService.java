package ru.gbteam.lms.service;

import lombok.AllArgsConstructor;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.repository.LessonRepository;
import ru.gbteam.lms.repository.ModuleRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MapperService {
    /**
        CourseRepository здесь - это конечно спорный вопрос, желательно бы сюда инжектить сервис.
        Но, если заинжектить CourseServiceImpl, мы получим циклическую зависимость,
        потому что в CourseServiceImpl скорей всего мы захотим маппить.
        Другое решение, как нам предлагал Артур Карапетов, сделать слой Policy.
        Но я бы не стал усложнять.
     */
    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    public CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setAuthor(course.getAuthor());
        dto.setTitle(course.getTitle());
        return dto;
    }

    public Course fromDTO(CourseDTO dto) {
        Course course = new Course();
        if (dto.getId() != null) {
            course = courseRepository.findById(dto.getId()).orElse(new Course());
        }
        course.setAuthor(dto.getAuthor());
        course.setTitle(dto.getTitle());
        return course;
    }

    public LessonDTO toDTO(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setContent(lesson.getContent());
        dto.setExercise(lesson.getExercise());
        dto.setModuleId(lesson.getModule().getId());
        dto.setModuleTitle(lesson.getModule().getTitle());
        return dto;
    }

    public Lesson fromDTO(LessonDTO dto) {
        Lesson lesson = new Lesson();
        if (dto.getId() != null) {
            lesson = lessonRepository.findById(dto.getId()).orElse(new Lesson());
        }
        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setContent(dto.getContent());
        lesson.setExercise(dto.getExercise());
        lesson.setModule(moduleRepository.findById(dto.getModuleId()).get());
        return lesson;
    }
}
