package ru.gbteam.lms.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.exception.InternalServerError;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceFacadeImpl implements CourseServiceFacade {
    private final CourseImageStorageService courseImageStorageService;
    private final CourseService courseService;
    private final ModuleService moduleService;
    private final UserService userService;
    private final MapperService mapperService;
    private final PaginationService paginationService;

    @Override
    @Transactional
    public void unAssignUser(Long courseId, Long userId) {
        userService.unAssign(courseId, userId);
    }

    @Override
    public void updateCourseImage(Long id, MultipartFile courseImage, HttpServletRequest request) {
        try {
            courseImageStorageService
                    .save(id, courseImage.getContentType(), courseImage.getInputStream());
        } catch (Exception ex) {
            throw new InternalServerError("upload_failed");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<byte[]> getCourseImage(Long courseId) {
        String contentType;
        byte[] data;
        if (courseImageStorageService.checkCourseImage(courseId)) {
            contentType = courseImageStorageService.getContentTypeByCourse(courseId)
                    .orElseThrow(() -> new NotFoundException("ContentType not found for", courseId));

            data = courseImageStorageService.getCourseImageByCourse(courseId)
                    .orElseThrow(() -> new NotFoundException("Data not found for", courseId));
        } else {
            contentType = "image/jpg";
            data = courseImageStorageService.getDefaultCourseImageData()
                    .orElseThrow(() -> new NotFoundException("Data not found for", courseId));
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(data);
    }

    @Override
    @Transactional
    public void assignUser(Long courseId, Long userId) {
        userService.assign(courseId, userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @Override
    public Course findCourseById(Long id) {
        return courseService.findById(id).orElseThrow(() -> new NotFoundException("Курс", id));
    }

    @Override
    public List<Module> findAllModulesByCourseId(Long id) {
        return moduleService.findAllByCourseId(id);
    }

    @Override
    public void deleteCourse(Long id) {
        courseService.delete(id);
    }

    @Override
    public void saveCourse(CourseDTO courseDTO) {
        Course c = mapperService.fromDTO(courseDTO);
        courseService.save(c);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseService.findAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Course> findPaginated(Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return (Page<Course>) paginationService.findPaginated(page, size,
                courseService.findCoursesByTitleOrAuthorLike(titlePrefix == null ? "" : titlePrefix));
    }

    @Override
    public List<Integer> getPageNumbers(Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return paginationService.getLessonPageNumbers(page, size,
                courseService.findCoursesByTitleOrAuthorLike(titlePrefix == null ? "" : titlePrefix));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Module> findModulePaginated(Long course_id, Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return (Page<Module>) paginationService.findPaginated(page, size,
                moduleService.findModulesByCourseIdAndTitleLike(course_id,(titlePrefix == null ? "" : titlePrefix)));
    }

    @Override
    public List<Integer> getModulePageNumbers(Long course_id, Optional<Integer> page, Optional<Integer> size, String titlePrefix) {
        return paginationService.getLessonPageNumbers(page, size,
                moduleService.findModulesByCourseIdAndTitleLike(course_id,(titlePrefix == null ? "" : titlePrefix)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<User> findUserPaginated(Optional<Integer> page, Optional<Integer> size) {
        return (Page<User>) paginationService.findPaginated(page, size,
                userService.findAll());
    }

    @Override
    public List<Integer> getUserPageNumbers(Optional<Integer> page, Optional<Integer> size, Model model) {
        return paginationService.getLessonPageNumbers(page, size,
                userService.findAll());
    }
}
