package ru.gbteam.lms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import ru.gbteam.lms.exception.NotFoundException;

class ExceptionHandlerControllerTest {

  @Test
  void testNotFoundExceptionHandler() {
    ExceptionHandlerController exceptionHandlerController = new ExceptionHandlerController();
    ModelAndView actualNotFoundExceptionHandlerResult = exceptionHandlerController
        .notFoundExceptionHandler(new NotFoundException());
    assertTrue(actualNotFoundExceptionHandlerResult.isReference());
    assertEquals(HttpStatus.NOT_FOUND, actualNotFoundExceptionHandlerResult.getStatus());
  }
}
