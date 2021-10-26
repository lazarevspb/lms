package ru.gbteam.lms.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gbteam.lms.controller.impl.CourseControllerImpl;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.service.CourseServiceFacade;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CourseControllerImpl.class})
@ExtendWith(SpringExtension.class)
class CourseControllerTest {
    @Autowired
    private CourseControllerImpl courseController;

    @MockBean
    private CourseServiceFacade courseService;

    @MockBean
    private ModuleService moduleService;

    @MockBean
    private UserService userService;

    @Test
    void testAssignUser() throws Exception {
        User user = new User();
        user.setCourses(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userService.findById(123L)).thenReturn(ofResult);

        Course course = new Course();
        course.setUsers(new HashSet<>());
        Optional<Course> ofResult1 = Optional.of(course);
        when(courseService.findCourseById(123L)).thenReturn(course);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders
                .post("/course/{courseId}/assign", 123L);
        MockHttpServletRequestBuilder requestBuilder = postResult.param("userId", String.valueOf(123L));
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
    }

    @Test
    void testCourseForm() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/new");
        MockMvcBuilders.standaloneSetup(courseController)
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
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/course/new");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.view().name("course_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
    }

    @Test
    void testCourseForm3() throws Exception {
        when(courseService.findUserPaginated(any(), any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(courseService.getUserPageNumbers(any(), any(), any())).thenReturn(new ArrayList<>());

        Course course = new Course();
        course.setUsers(new HashSet<>());

        when(courseService.findCourseById(any())).thenReturn(course);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/{id}", 123L);
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course", "modulePage", "userPage"))
                .andExpect(MockMvcResultMatchers.view().name("course_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
    }

    @Test
    void testCourseTable() throws Exception {
        Course course = new Course();
        course.setUsers(new HashSet<>());
        when(courseService.findPaginated(Optional.of(1), Optional.of(10), "Курс П")).thenReturn(new PageImpl<>(List.of(course)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course").param("title", "Курс П");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("coursePage"))
                .andExpect(MockMvcResultMatchers.view().name("course_table"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
    }

    @Test
    void testCourseTable2() throws Exception {
        when(courseService.findPaginated(Optional.of(1), Optional.of(10), null)).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/course");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("coursePage"))
                .andExpect(MockMvcResultMatchers.view().name("course_table"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(123L);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/course/{id}", 123L);
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
    }

    @Test
    void testSaveCourse() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/course/save")
                .param("title", "Курс по джава")
                .param("author", "Пушкин");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
    }

    @Test
    void testUnAssignUser() throws Exception {
        User user = new User();
        user.setCourses(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userService.findById(100L)).thenReturn(ofResult);

        Course course = new Course();
        course.setUsers(new HashSet<>());
        Optional<Course> ofResult1 = Optional.of(course);
        when(courseService.findCourseById(123L)).thenReturn(course);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/course/{courseId}/unassign/{userId}",
                123L, 100L);
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course/123"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/123"));
    }
}

