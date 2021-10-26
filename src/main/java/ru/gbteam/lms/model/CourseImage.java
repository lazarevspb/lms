package ru.gbteam.lms.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_images")
public class CourseImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String contentType;

  @Column
  private String filename;

  @OneToOne
  private Course course;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CourseImage)) return false;
    CourseImage that = (CourseImage) o;
    return getId().equals(that.getId()) && getFilename().equals(that.getFilename());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getFilename());
  }
}
