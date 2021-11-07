package ru.gbteam.lms.controller.impl;

import org.springframework.stereotype.Controller;
import ru.gbteam.lms.controller.IndexController;

@Controller
public class IndexControllerImpl implements IndexController {

  @Override
  public String index() {
    return "redirect:/course";
  }
}

