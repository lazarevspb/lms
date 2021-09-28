package ru.gbteam.lms.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CourseServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CourseServiceImplTest {

  @MockBean
  private CourseRepository courseRepository;

  @Autowired
  private CourseServiceImpl courseServiceImpl;


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
  void testDelete() {
    this.courseServiceImpl.delete(123L);
    assertTrue(this.courseServiceImpl.findAll().isEmpty());
  }
}

