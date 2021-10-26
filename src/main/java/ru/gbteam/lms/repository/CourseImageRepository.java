package ru.gbteam.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gbteam.lms.model.CourseImage;

import java.util.Optional;

@Repository
public interface CourseImageRepository extends JpaRepository<CourseImage, Long> {

    Optional<CourseImage> findByFilename(String filename);

    boolean existsCourseImageByCourse_Id(Long courseId);
}
