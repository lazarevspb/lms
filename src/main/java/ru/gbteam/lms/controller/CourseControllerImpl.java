package ru.gbteam.lms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gbteam.lms.controller.impl.CourseController;
import ru.gbteam.lms.dto.CourseDTO;
import ru.gbteam.lms.exception.InternalServerError;
import ru.gbteam.lms.exception.NotFoundException;
import ru.gbteam.lms.model.Course;
import ru.gbteam.lms.service.CourseImageStorageService;
import ru.gbteam.lms.service.CourseServiceFacade;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CourseControllerImpl implements CourseController {
    private final CourseServiceFacade courseServiceFacade;
    private final CourseImageStorageService courseImageStorageService;

    @GetMapping("/courseImage/{courseId}")
    @ResponseBody
    public ResponseEntity<byte[]> courseImage(@PathVariable Long courseId) {
        String contentType;
        byte[] data;
        if (courseImageStorageService.checkCourseImage(courseId)) {
            contentType = courseImageStorageService.getContentTypeByCourse(courseId)
                    .orElseThrow(() -> new NotFoundException("ContentType not found for", courseId));

            data = courseImageStorageService.getCourseImageByCourse(courseId)
                    .orElseThrow(() -> new NotFoundException("Data not found for", courseId));
        } else {
            contentType = "image/jpg";
            data = courseImageStorageService.getDefaultCourseImageData()
                    .orElseThrow(() -> new NotFoundException("Data not found for", courseId));
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(data);
    }

    @PostMapping("/courseImage")
    public String updateCourseImage(
            @RequestParam Long id,
            @RequestParam("courseImage") MultipartFile courseImage,
            HttpServletRequest request) {

        try {
            courseImageStorageService
                    .save(id, courseImage.getContentType(), courseImage.getInputStream());
        } catch (Exception ex) {
            throw new InternalServerError("upload_failed");
        }
        return String.format("redirect:%s", request.getHeader("referer"));
    }

    public String unAssignUser(Long courseId, Long userId) {
        courseServiceFacade.unAssignUser(courseId, userId);
        return String.format("redirect:/course/%d", courseId);
    }

    public String assignCourse(Model model, String courseId) {
        model.addAttribute("users", courseServiceFacade.findAllUsers()); // TODO: 04.10.2021 filtering user availability add
        return "assign";
    }

    public String assignUser(Long courseId, Long userId) {
        courseServiceFacade.assignUser(courseId, userId);
        return "redirect:/course";
    }

    public String deleteCourse(Long id) {
        courseServiceFacade.deleteCourse(id);
        return "redirect:/course";
    }

    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }

    public String saveCourse(CourseDTO courseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course_form";
        }
        courseServiceFacade.saveCourse(courseDto);
        return "redirect:/course";
    }

    public String courseForm(Model model, Long id, Optional<Integer> modulePage, Optional<Integer> moduleSize,
                             Optional<Integer> userPage, Optional<Integer> userSize) {
        final Course course = courseServiceFacade.findCourseById(id);

        model.addAttribute("modulePage", courseServiceFacade.findModulePaginated(id, modulePage, moduleSize));
        List<Integer> pageNumbers = courseServiceFacade.getModulePageNumbers(id, modulePage, moduleSize);
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

    public String courseTable(Model model, Optional<Integer> page, Optional<Integer> size, String title) {

        model.addAttribute("coursePage", courseServiceFacade.findPaginated(page, size, title));
        List<Integer> pageNumbers = courseServiceFacade.getPageNumbers(page, size, title);
        if (!CollectionUtils.isEmpty(pageNumbers)) {
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "course_table";
    }
}
