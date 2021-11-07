package ru.gbteam.lms.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gbteam.lms.controller.impl.ModuleControllerImpl;
import ru.gbteam.lms.model.*;
import ru.gbteam.lms.model.Module;
import ru.gbteam.lms.service.ModuleServiceFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ModuleControllerImpl.class})
@ExtendWith(SpringExtension.class)
public class ModuleControllerTest {
    @Autowired
    private ModuleControllerImpl moduleController;

    @MockBean
    private ModuleServiceFacade moduleServiceFacade;

    @Test
    void testNewModule() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/module/new").param("course_id", "1");
        when(moduleServiceFacade.findCourseById(1L)).thenReturn(Course.builder().id(1L).title("Курс").build());

        MockMvcBuilders.standaloneSetup(moduleController)
                .build()
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("module"))
                .andExpect(MockMvcResultMatchers.view().name("module_form"));
    }

    @Test
    void testModuleForm() throws Exception {
        when(moduleServiceFacade.findLessonPaginated(anyLong(), Optional.of(anyInt()), Optional.of(anyInt()), anyString())).thenReturn(new PageImpl<>(List.of(Lesson.builder().id(123L).build()), PageRequest.of(0, 10), 1));
        when(moduleServiceFacade.getLessonPageNumbers(any(), any(), any(), any())).thenReturn(new ArrayList<>());
        when(moduleServiceFacade.findModuleById(any())).thenReturn(Module.builder().id(1L).title("Module").text("Text").course(Course.builder().id(2L).build()).build());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/module/{id}", 123L);
        MockMvcBuilders.standaloneSetup(moduleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("module"))
                .andExpect(MockMvcResultMatchers.view().name("module_form"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("module_form"));
    }

    @Test
    void testDeleteModule() throws Exception {
        doNothing().when(moduleServiceFacade).deleteModule(123L);
        when(moduleServiceFacade.findModuleById(123L)).thenReturn(Module.builder().id(123L).title("Модуль").course(Course.builder().id(2L).build()).build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/module/{id}", 123L);
        MockMvcBuilders.standaloneSetup(moduleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/course/2"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/course/2"));
    }
}
