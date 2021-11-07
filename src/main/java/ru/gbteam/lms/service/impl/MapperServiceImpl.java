package ru.gbteam.lms.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.dto.ModuleDTO;
import ru.gbteam.lms.dto.UserDTO;
import ru.gbteam.lms.dto.UserWithPwdDto;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.repository.ModuleRepository;
import ru.gbteam.lms.repository.UserRepository;
import ru.gbteam.lms.service.MapperService;

@Service
@AllArgsConstructor
public class MapperServiceImpl implements MapperService {
    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public CourseDTO toDTO(Course course) {
        return CourseDTO.builder().id(course.getId()).author(course.getAuthor()).title(course.getTitle()).build();
    }

    @Override
    public Course fromDTO(CourseDTO dto) {
        return Course.builder().id(dto.getId()).author(dto.getAuthor()).title(dto.getTitle()).build();
    }

    @Override
    public ModuleDTO toDTO(Module module) {
        return ModuleDTO.builder().id(module.getId())
                .title(module.getTitle()).text(module.getText())
                .courseId(module.getCourse().getId()).build();
    }

    @Override
    public Module fromDTO(ModuleDTO dto) {
        return Module.builder().id(dto.getId())
                .title(dto.getTitle()).text(dto.getText())
                .course(courseRepository.getById(dto.getCourseId())).build();
    }

    @Override
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

    @Override
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

    @Override
    public UserWithPwdDto toUserRegistrationDTO(User user) {
        return UserWithPwdDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .courses(user.getCourses())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public User fromUserRegistrationDTO(UserWithPwdDto dto) {
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .courses(dto.getCourses())
                .roles(dto.getRoles())
                .build();
    }

    @Override
    public UserDTO toUserAuthDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .courses(user.getCourses())
                .roles(user.getRoles())
                .build();
    }
    @Override
    public User fromUserAuthDTO(UserDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь", dto.getId()));

        return user.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .courses(dto.getCourses())
                .roles(dto.getRoles())
                .password(user.getPassword())
                .build();
    }
}
