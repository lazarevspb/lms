package ru.gbteam.lms.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.repository.impl.MemoryBasedCourseRepository;

@ContextConfiguration(classes = {CourseServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CourseServiceImplTest {

  @MockBean
  private CourseRepository courseRepository;

  @Autowired
  private CourseServiceImpl courseServiceImpl;

  @Test
  void testConstructor() {
    assertTrue((new CourseServiceImpl(new MemoryBasedCourseRepository())).findAll().isEmpty());
  }

  @Test
  void testFindAll() {
    ArrayList<Course> courseList = new ArrayList<>();
    when(this.courseRepository.findAll()).thenReturn(courseList);
    List<Course> actualFindAllResult = this.courseServiceImpl.findAll();
    assertSame(courseList, actualFindAllResult);
    assertTrue(actualFindAllResult.isEmpty());
    verify(this.courseRepository).findAll();
  }

  @Test
  void testFindById() {
    Optional<Course> ofResult = Optional.of(new Course());
    when(this.courseRepository.findById(anyLong())).thenReturn(ofResult);
    Optional<Course> actualFindByIdResult = this.courseServiceImpl.findById(123L);
    assertSame(ofResult, actualFindByIdResult);
    assertTrue(actualFindByIdResult.isPresent());
    verify(this.courseRepository).findById(anyLong());
    assertTrue(this.courseServiceImpl.findAll().isEmpty());
  }

  @Test
  void testSave() {
    doNothing().when(this.courseRepository).save(any());
    this.courseServiceImpl.save(new Course());
    verify(this.courseRepository).save(any());
    assertTrue(this.courseServiceImpl.findAll().isEmpty());
  }

  @Test
  void testDelete() {
    doNothing().when(this.courseRepository).delete(anyLong());
    this.courseServiceImpl.delete(123L);
    verify(this.courseRepository).delete(anyLong());
    assertTrue(this.courseServiceImpl.findAll().isEmpty());
  }
}

