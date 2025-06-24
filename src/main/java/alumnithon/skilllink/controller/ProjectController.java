package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.project.dto.ProjectCreateDTO;
import alumnithon.skilllink.domain.learning.project.dto.ProjectPreviewDTO;
import alumnithon.skilllink.domain.learning.project.service.ProjectService;
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
@RequestMapping("/projects")
@Tag(name = "Gestión de proyectos", description = "Endpoints para el modulo de gestios de proyectos.")
@SecurityRequirement(name = "Bearer Authentication")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // crear Proyectos
    @PostMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ProjectPreviewDTO> createProject(@Valid @RequestBody ProjectCreateDTO dto) {
        ProjectPreviewDTO created = projectService.createProjectByMentor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
