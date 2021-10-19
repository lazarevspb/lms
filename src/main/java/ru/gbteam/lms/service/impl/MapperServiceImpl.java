package ru.gbteam.lms.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.dto.ModuleDTO;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.repository.ModuleRepository;
import ru.gbteam.lms.service.MapperService;

@Service
@AllArgsConstructor
public class MapperServiceImpl implements MapperService {
    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;

    public CourseDTO toDTO(Course course) {
        return CourseDTO.builder().id(course.getId()).author(course.getAuthor()).title(course.getTitle()).build();
    }

    public Course fromDTO(CourseDTO dto) {
        return Course.builder().id(dto.getId()).author(dto.getAuthor()).title(dto.getTitle()).build();
    }

    public ModuleDTO toDTO(Module module) {
        return ModuleDTO.builder().id(module.getId())
                .title(module.getTitle()).text(module.getText())
                .courseId(module.getCourse().getId()).build();
    }

    public Module fromDTO(ModuleDTO dto) {
        return Module.builder().id(dto.getId())
                .title(dto.getTitle()).text(dto.getText())
                .course(courseRepository.getById(dto.getCourseId())).build();
    }

    public LessonDTO toDTO(Lesson lesson) {
        return LessonDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .content(lesson.getContent())
                .exercise(lesson.getExercise())
                .moduleId(lesson.getModule().getId())
                .moduleTitle(lesson.getModule().getTitle())
                .build();
    }

    public Lesson fromDTO(LessonDTO dto) {
        return Lesson.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .content(dto.getContent())
                .exercise(dto.getExercise())
                .module(moduleRepository.getById(dto.getModuleId()))
                .build();
    }

    public UserDto toDTO(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .courses(user.getCourses())
                .roles(user.getRoles())
                .build();
    }

    public User fromDTO(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .courses(dto.getCourses())
                .roles(dto.getRoles())
                .build();
    }
}
