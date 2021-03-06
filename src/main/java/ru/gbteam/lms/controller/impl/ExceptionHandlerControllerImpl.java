package ru.gbteam.lms.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import ru.gbteam.lms.controller.ExceptionHandlerController;
import ru.gbteam.lms.exception.NotFoundException;

@ControllerAdvice
public class ExceptionHandlerControllerImpl implements ExceptionHandlerController {

    @Override
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("exception", ex);
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
