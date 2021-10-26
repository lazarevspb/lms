package ru.gbteam.lms.service;

import org.springframework.transaction.annotation.Transactional;
import ru.gbteam.lms.model.CourseImage;

import java.io.InputStream;
import java.util.Optional;

public interface CourseImageStorageService {
    boolean checkCourseImage(Long courseId);

    Optional<byte[]> getDefaultCourseImageData();

    Optional<String> getContentTypeByCourse(Long courseId);

    Optional<byte[]> getCourseImageByCourse(Long courseId);

    Optional<CourseImage> getCourseImagedByCourseId(Long courseId);

    @Transactional
    void save(Long courseId, String contentType, InputStream is);
}
