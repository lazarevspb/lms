package ru.gbteam.lms.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public interface ErrorController {

    @RequestMapping("/error")
    String handleError(HttpServletRequest request);
}
