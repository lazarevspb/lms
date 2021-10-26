package ru.gbteam.lms.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.CourseImage;
import ru.gbteam.lms.repository.CourseImageRepository;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.service.CourseImageStorageService;
import ru.gbteam.lms.util.ReadData;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.*;

@Slf4j
@Service
public class CourseImageStorageServiceImpl implements CourseImageStorageService {

    private final CourseImageRepository courseImageRepository;
    private final CourseRepository courseRepository;

    @Value("${file.storage.path}")
    private String path;

    @Autowired
    public CourseImageStorageServiceImpl(
            CourseImageRepository courseImageRepository,
            CourseRepository courseRepository) {
        this.courseImageRepository = courseImageRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public boolean checkCourseImage(Long courseId) {
        return courseImageRepository.existsCourseImageByCourse_Id(courseId);
    }

    @Override
    public Optional<byte[]> getDefaultCourseImageData() {
        String DEFAULT_IMAGE_FILENAME = "default_course_image.png";
        return ReadData.read(path, DEFAULT_IMAGE_FILENAME);
    }

    /**
     * @return возвращает тип файла изображения
     */
    @Override
    public Optional<String> getContentTypeByCourse(Long courseId) {
        return getCourseImagedByCourseId(courseId)
                .map(CourseImage::getContentType);
    }

    /**
     * @return файл изображения в виде массива байтов
     */
    @Override
    public Optional<byte[]> getCourseImageByCourse(Long courseId) {
        return getCourseImagedByCourseId(courseId)
                .map(CourseImage::getFilename)
                .map(filename -> ReadData.read(path, filename)
                        .orElseThrow(() -> new IllegalStateException("no_data")));
    }

    @Override
    public Optional<CourseImage> getCourseImagedByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found", courseId));
        return Optional.ofNullable(course.getCourseImage());
    }

    @Override
    @Transactional
    public void save(Long courseId, String contentType, InputStream is) {
        Optional<Course> course = courseRepository.findById(courseId);
        Optional<CourseImage> imageOptional = Optional.ofNullable(course.orElseThrow(
                () -> new NotFoundException("Course_not_found", courseId)).getCourseImage());
        CourseImage courseImage;
        String filename;
        if (imageOptional.isEmpty()) {
            filename = UUID.randomUUID().toString();
            Course courseOptional = courseRepository.findById(courseId)
                    .orElseThrow(IllegalArgumentException::new);
            courseImage = new CourseImage(null, contentType, filename, courseOptional);
        } else {
            courseImage = imageOptional.get();
            filename = courseImage.getFilename();
            courseImage.setContentType(contentType);
        }
        courseImageRepository.save(courseImage);

        try (OutputStream os = newOutputStream(Path.of(path, filename), CREATE, WRITE,
                TRUNCATE_EXISTING)) {
            is.transferTo(os);
        } catch (Exception ex) {
            log.error("Can't write to file {}", filename, ex);
            throw new IllegalStateException(ex);
        }
    }
}
