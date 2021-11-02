package ru.gbteam.lms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import ru.gbteam.lms.controller.CourseController;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.service.CourseServiceFacade;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController {
    private final CourseServiceFacade courseServiceFacade;

    @Override
    public ResponseEntity<byte[]> courseImage(Long courseId) {
     return courseServiceFacade.getCourseImage(courseId);
    }

    @Override
    public String updateCourseImage(Long id, MultipartFile courseImage, HttpServletRequest request) {
        courseServiceFacade.updateCourseImage(id, courseImage, request);
        return String.format("redirect:%s", request.getHeader("referer"));
    }

    @Override
    public String unAssignUser(Long courseId, Long userId) {
        courseServiceFacade.unAssignUser(courseId, userId);
        return String.format("redirect:/course/%d", courseId);
    }

    @Override
    public String assignCourse(Model model, String courseId) {
        model.addAttribute("users", courseServiceFacade.findAllUsers()); // TODO: 04.10.2021 filtering user availability add
        return "assign";
    }

    @Override
    public String assignUser(Long courseId, Long userId) {
        courseServiceFacade.assignUser(courseId, userId);
        return "redirect:/course";
    }

    @Override
    public String deleteCourse(Long id) {
        courseServiceFacade.deleteCourse(id);
        return "redirect:/course";
    }

    @Override
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @Override
    public String saveCourse(CourseDTO courseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course_form";
        }
        courseServiceFacade.saveCourse(courseDto);
        return "redirect:/course";
    }

    @Override
    public String courseForm(Model model, Long id, Optional<Integer> modulePage, Optional<Integer> moduleSize, String title,
                             Optional<Integer> userPage, Optional<Integer> userSize) {
        final Course course = courseServiceFacade.findCourseById(id);

        model.addAttribute("modulePage", courseServiceFacade.findModulePaginated(id, modulePage, moduleSize, title));
        List<Integer> pageNumbers = courseServiceFacade.getModulePageNumbers(id, modulePage, moduleSize, title);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageModelNumbers", pageNumbers);
        }

        model.addAttribute("userPage", courseServiceFacade.findUserPaginated(userPage, userSize));
        List<Integer> pageUserNumbers = courseServiceFacade.getUserPageNumbers(userPage, userSize, model);
        if (!CollectionUtils.isEmpty(pageUserNumbers)) {
            model.addAttribute("pageUserNumbers", pageUserNumbers);
        }

        model.addAttribute("course", course);
        return "course_form";
    }

    @Override
    public String courseTable(Model model, Optional<Integer> page, Optional<Integer> size, String title) {

        model.addAttribute("coursePage", courseServiceFacade.findPaginated(page, size, title));
        List<Integer> pageNumbers = courseServiceFacade.getPageNumbers(page, size, title);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "course_table";
    }
}
