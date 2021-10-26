package ru.gbteam.lms.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gbteam.lms.controller.impl.LessonController;
import ru.gbteam.lms.service.LessonServiceFacade;

@ContextConfiguration(classes = {CourseControllerImpl.class})
@ExtendWith(SpringExtension.class)
public class LessonControllerTest {

    @Autowired
    private LessonController lessonController;

    @MockBean
    private LessonServiceFacade lessonServiceFacade;

    @Test
    void testGet() {

    }
}
