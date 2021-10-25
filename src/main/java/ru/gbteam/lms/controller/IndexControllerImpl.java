package ru.gbteam.lms.controller;

import org.springframework.stereotype.Controller;

@Controller
public class IndexControllerImpl {

  public String index() {
    return "redirect:/course";
  }
}
