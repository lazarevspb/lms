package ru.gbteam.lms.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.service.CourseService;

@ContextConfiguration(classes = {CourseController.class})
@ExtendWith(SpringExtension.class)
class CourseControllerTest {

  @Autowired
  private CourseController courseController;

  @MockBean
  private CourseService courseService;

  @Test
  void testCourseForm() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/new");
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
        .andExpect(MockMvcResultMatchers.view().name("course_form"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
  }

  @Test
  void testCourseForm2() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/new",
        "Uri Vars");
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
        .andExpect(MockMvcResultMatchers.view().name("course_form"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
  }

  @Test
  void testCourseForm3() throws Exception {
    when(this.courseService.findById(any())).thenReturn(Optional.of(new Course()));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/{id}", 123L);
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
        .andExpect(MockMvcResultMatchers.view().name("course_form"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
  }

  @Test
  void testCourseTable() throws Exception {
    when(this.courseService.findAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course");
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
        .andExpect(MockMvcResultMatchers.view().name("course_table"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
  }

  @Test
  void testCourseTable2() throws Exception {
    when(this.courseService.findAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/course");
    getResult.contentType("Not all who wander are lost");
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(getResult)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
        .andExpect(MockMvcResultMatchers.view().name("course_table"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
  }

  @Test
  void testDeleteCourse() throws Exception {
    doNothing().when(this.courseService).delete(any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/course/{id}",
        123L);
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.model().size(0))
        .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
        .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
  }

  @Test
  void testDeleteCourse2() throws Exception {
    doNothing().when(this.courseService).delete(any());
    MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/course/{id}",
        123L);
    deleteResult.contentType("Not all who wander are lost");
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(deleteResult)
        .andExpect(MockMvcResultMatchers.status().isFound())
        .andExpect(MockMvcResultMatchers.model().size(0))
        .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
        .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
  }

  @Test
  void testSaveCourse() throws Exception {
    when(this.courseService.findAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course");
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
        .andExpect(MockMvcResultMatchers.view().name("course_table"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
  }

  @Test
  void testSaveCourse2() throws Exception {
    when(this.courseService.findAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/course");
    getResult.contentType("Not all who wander are lost");
    MockMvcBuilders.standaloneSetup(this.courseController)
        .build()
        .perform(getResult)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().size(1))
        .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
        .andExpect(MockMvcResultMatchers.view().name("course_table"))
        .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
  }
}

