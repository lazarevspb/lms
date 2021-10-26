 package ru.gbteam.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;

public interface IndexController {

    @GetMapping(value = {"/", ""})
    String index();

}
