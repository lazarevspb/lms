package ru.gbteam.lms.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControllerImpl {

  @GetMapping(value = {"/", ""})
  public String index() {
    return "redirect:/course";
  }
}
