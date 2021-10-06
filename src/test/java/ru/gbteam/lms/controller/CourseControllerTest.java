package ru.gbteam.lms.controller;

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
import org.springframework.ui.ConcurrentModel;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.model.User;
import ru.gbteam.lms.repository.CourseRepository;
import ru.gbteam.lms.repository.ModuleRepository;
import ru.gbteam.lms.repository.UserRepository;
import ru.gbteam.lms.service.CourseService;
import ru.gbteam.lms.service.ModuleService;
import ru.gbteam.lms.service.RoleService;
import ru.gbteam.lms.service.UserService;
import ru.gbteam.lms.service.impl.CourseServiceImpl;
import ru.gbteam.lms.service.impl.ModuleServiceImpl;
import ru.gbteam.lms.service.impl.UserDtoService;
import ru.gbteam.lms.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CourseController.class})
@ExtendWith(SpringExtension.class)
class CourseControllerTest {
    @Autowired
    private CourseController courseController;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ModuleService moduleService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDtoService userDtoService;

    @MockBean
    private RoleService roleService;

    @Test
    void testAssignUser() throws Exception {
        User user = new User();
        user.setCourses(new HashSet<>());
        user.setId(123L);
        Optional<User> ofResult = Optional.of(user);
        when(userService.findById(123L)).thenReturn(ofResult);

        Course course = new Course();
        course.setUsers(new HashSet<>());
        course.setId(123L);
        Optional<Course> ofResult1 = Optional.of(course);
        when(courseService.findById(123L)).thenReturn(ofResult1);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders
                .post("/course/{courseId}/assign", 123L);
        MockHttpServletRequestBuilder requestBuilder = postResult.param("userId", String.valueOf(123L));
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
    }

    @Test
    void testAssignCourse() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        CourseServiceImpl courseService = new CourseServiceImpl(mock(CourseRepository.class));
        CourseController courseController = new CourseController(courseService,
                new ModuleServiceImpl(mock(ModuleRepository.class)), userService, userDtoService, roleService);
        assertEquals("assign", courseController.assignCourse(new ConcurrentModel(), "1"));
        verify(userRepository).findAll();
    }

    @Test
    void testCourseForm() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/new");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.view().name("course_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
    }

    @Test
    void testCourseForm2() throws Exception {
        Course course = new Course();
        course.setUsers(new HashSet<>());
        course.setId(123L);
        Optional<Course> ofResult1 = Optional.of(course);
        when(courseService.findById(123L)).thenReturn(ofResult1);

        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/course/new");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.view().name("course_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"))
        ;
    }

    @Test
    void testCourseForm3() throws Exception {
        when(moduleService.findAllByCourseId(123L)).thenReturn(new ArrayList<>());

        Course course = new Course();
        course.setUsers(new HashSet<>());
        course.setId(123L);

        Optional<Course> ofResult = Optional.of(course);
        when(courseService.findById(any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/{id}", 123L);
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course", "modules", "users"))
                .andExpect(MockMvcResultMatchers.view().name("course_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
    }

    @Test
    void testCourseForm4() throws Exception {
        when(roleService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course/new");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course", "roles"))
                .andExpect(MockMvcResultMatchers.view().name("course_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_form"));
    }

    @Test
    void testCourseTable() throws Exception {
        ArrayList<Course> listCourses = new ArrayList<>(Collections.singleton(new Course(1L, null, null, null, null)));
        when(courseService.findAll()).thenReturn(listCourses);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/course");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.view().name("course_table"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
    }

    @Test
    void testCourseTable2() throws Exception {
        when(courseService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/course");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.view().name("course_table"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("course_table"));
    }

    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(courseService).delete(123L);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/course/{id}", 123L);
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
    }

    @Test
    void testSaveCourse() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/course/save");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("course"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course"));
    }

    @Test
    void testSaveCourse2() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/course/save");
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
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
        course.setId(123L);
        Optional<Course> ofResult1 = Optional.of(course);
        when(courseService.findById(123L)).thenReturn(ofResult1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/course/{courseId}/unassign/{userId}",
                123L, 100L);
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course/123"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/123"));
    }
}

