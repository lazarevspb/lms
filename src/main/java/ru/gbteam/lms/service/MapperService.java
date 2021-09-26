package ru.gbteam.lms.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.repository.CourseRepository;

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
}
