package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseDetailDTO;
import alumnithon.skilllink.domain.learning.course.dto.CoursePreviewDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseUpdateDTO;
import alumnithon.skilllink.domain.learning.course.service.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Tag(name = "Gestión de Cursos", description = "Endpoints para el modulo de Cursos.")
@SecurityRequirement(name = "Bearer Authentication")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Page<CoursePreviewDTO>> getAllCoursesByMentor(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(courseService.getAllEnabledCoursesByMentor(pageable));
    }

    @GetMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<CourseDetailDTO> getCourseByIdForMentor(@PathVariable Long id) {
        CourseDetailDTO course = courseService.getEnabledCourseByIdForMentor(id);
        return ResponseEntity.ok(course);
    }

    // crear Cursos
    @PostMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<CoursePreviewDTO> createCourse(@Valid @RequestBody CourseCreateDTO dto) {
        CoursePreviewDTO created = courseService.createCourseByMentor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Editar Cursos
    @PutMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<CourseDetailDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateDTO dto) {
        return ResponseEntity.ok(courseService.updateCourseByMentor(id, dto));
    }
    //Deshabilitar Cursos
    @DeleteMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseByMentor(id);
        return ResponseEntity.noContent().build();
    }

    //<---- Rutas para todos los los usuarios autenticados  ---->

    @GetMapping
    public ResponseEntity<List<CoursePreviewDTO>> getAllCourses() {
        List<CoursePreviewDTO> courses = courseService.getAllEnabledCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailDTO> getCourseById(@PathVariable Long id) {
        CourseDetailDTO course = courseService.getEnabledCourseById(id);
        return ResponseEntity.ok(course);
    }
}
