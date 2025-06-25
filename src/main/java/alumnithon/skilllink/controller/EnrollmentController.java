package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.enrollment.dto.EnrollmentContentDTO;
import alumnithon.skilllink.domain.learning.enrollment.dto.EnrollmentRequestDTO;
import alumnithon.skilllink.domain.learning.enrollment.service.EnrollmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mylearning")
@Tag(name = "Asigna un contenido de aprendizaje a un usuario", description = "Modulo permite asignar un proyecto, curso o desafio a un usuario")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('USER')")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping("/{contentId}/enroll")
    public ResponseEntity<Void> enrollUserToContent(
            @PathVariable Long contentId,
            @RequestBody @Valid EnrollmentRequestDTO dto
    ) {
        service.enrollCurrentUser(contentId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<EnrollmentContentDTO>> getMyEnrolledContent() {
        List<EnrollmentContentDTO> content = service.getEnrolledContentByUser();
        return ResponseEntity.ok(content);
    }
}
