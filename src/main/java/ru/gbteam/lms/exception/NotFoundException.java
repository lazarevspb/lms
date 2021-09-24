package ru.gbteam.lms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotFoundException extends RuntimeException {

  private String className;
  private Long id;

  public NotFoundException(String className, Long id) {
    this.className = className;
    this.id = id;
  }
}