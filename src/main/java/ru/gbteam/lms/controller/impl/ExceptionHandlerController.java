package ru.gbteam.lms.controller.impl;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.gbteam.lms.exception.NotFoundException;

public interface ExceptionHandlerController {
    @ExceptionHandler
    ModelAndView notFoundExceptionHandler(NotFoundException ex);
}
