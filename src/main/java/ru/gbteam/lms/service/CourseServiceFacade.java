package ru.gbteam.lms.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface CourseServiceFacade {

    void updateCourseImage(Long id, MultipartFile courseImage, HttpServletRequest request);

    @Transactional
    ResponseEntity<byte[]> getCourseImage(Long courseId);

    void unAssignUser(Long courseId, Long userId);

    void assignUser(Long courseId, Long userId);

    Course findCourseById(Long id);

    List<Module> findAllModulesByCourseId(Long id);

    void deleteCourse(Long id);

    void saveCourse(CourseDTO courseDTO);

    List<Course> findAllCourses();

    Page<Course> findPaginated(Optional<Integer> page, Optional<Integer> size, String titlePrefix);

    List<Integer> getPageNumbers(Optional<Integer> page, Optional<Integer> size, String titlePrefix);

    Page<Module> findModulePaginated(Long id, Optional<Integer> page, Optional<Integer> size);

    List<Integer> getModulePageNumbers(Long course_id, Optional<Integer> page, Optional<Integer> size);

    Page<User> findUserPaginated(Optional<Integer> page, Optional<Integer> size);

    List<Integer> getUserPageNumbers(Optional<Integer> page, Optional<Integer> size, Model model);

    List<User> findAllUsers();
}
