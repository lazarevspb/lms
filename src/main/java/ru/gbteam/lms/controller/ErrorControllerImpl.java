package ru.gbteam.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import ru.gbteam.lms.controller.impl.ErrorController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController {

  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());

      if (statusCode == HttpStatus.NOT_FOUND.value()) {
        return "errors/error-404";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        return "errors/error-500";
      }
    }
    return "errors/error";
  }
}
