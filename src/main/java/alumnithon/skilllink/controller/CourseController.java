package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CoursePreviewDTO;
import alumnithon.skilllink.domain.learning.course.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@Tag(name = "Gestión de Cursos", description = "Endpoints para el modulo de Cursos.")
@SecurityRequirement(name = "Bearer Authentication")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // crear Cursos
    @PostMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<CoursePreviewDTO> createCourse(@Valid @RequestBody CourseCreateDTO dto) {
        CoursePreviewDTO created = courseService.createCourseByMentor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
