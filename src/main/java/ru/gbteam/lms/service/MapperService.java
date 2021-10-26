package ru.gbteam.lms.service;

import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.dto.ModuleDTO;
import ru.gbteam.lms.dto.UserDto;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;

public interface MapperService {
    Lesson fromDTO(LessonDTO dto);
    LessonDTO toDTO(Lesson model);
    Course fromDTO(CourseDTO dto);
    CourseDTO toDTO(Course model);
    UserDto toDTO(User user);
    User fromDTO(UserDto dto);
    ModuleDTO toDTO(Module module);
     Module fromDTO(ModuleDTO dto);
}
