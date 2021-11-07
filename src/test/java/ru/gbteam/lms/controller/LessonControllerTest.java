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
import ru.gbteam.lms.controller.impl.LessonControllerImpl;
import ru.gbteam.lms.dto.LessonDTO;
import ru.gbteam.lms.model.Lesson;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.facade.LessonServiceFacadeImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {LessonControllerImpl.class})
@ExtendWith(SpringExtension.class)
public class LessonControllerTest {
    @Autowired
    private LessonControllerImpl lessonControllerImpl;

    @MockBean
    private LessonServiceFacadeImpl lessonServiceFasade;

    @Test
    void testGetLessonById() throws Exception {
        when(lessonServiceFasade.mapLessonToDto(any())).thenReturn(LessonDTO.builder().moduleTitle("Module1").title("Lesson1").id(any()).build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/lesson/{id}", 123L);
        MockMvcBuilders.standaloneSetup(lessonControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lesson"))
                .andExpect(MockMvcResultMatchers.view().name("lesson_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("lesson_form"));
    }

    @Test
    void testNewLesson() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/lesson/new")
                .param("module_id", "1");
        MockMvcBuilders.standaloneSetup(lessonControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lesson"))
                .andExpect(MockMvcResultMatchers.view().name("lesson_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("lesson_form"));
    }

    @Test
    void testSaveLesson() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lesson/save")
                .param("title", "Урок")
                .param("description", "Lesson description")
                .param("content", "Lesson content")
                .param("exercise", "Lesson exercise")
                .param("moduleId", "2");
        MockMvcBuilders.standaloneSetup(lessonControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lesson"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/module/2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/module/2"));
    }

    @Test
    void testSaveLesson2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/lesson/save")
                .param("title", "Lesson1")
                .param("description", "Lesson description")
                .param("content", "Lesson content")
                .param("exercise", "Lesson exercise")
                .param("moduleId", "2");
        MockMvcBuilders.standaloneSetup(lessonControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lesson"))
                .andExpect(MockMvcResultMatchers.view().name("lesson_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("lesson_form"));
    }

    @Test
    void testDeleteLesson() throws Exception {
        doNothing().when(lessonServiceFasade).deleteLesson(1L);
        when(lessonServiceFasade.findLessonById(1L)).thenReturn(Lesson.builder().id(1L).title("Урок").content("См приложение").module(Module.builder().id(2L).build()).build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/lesson/{lesson_id}", 1L);
        MockMvcBuilders.standaloneSetup(lessonControllerImpl)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/module/2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/module/2"));
    }

}
