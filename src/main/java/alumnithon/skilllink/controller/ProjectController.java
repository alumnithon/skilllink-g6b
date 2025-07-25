package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.project.dto.*;
import alumnithon.skilllink.domain.learning.project.service.ProjectContributionService;
import alumnithon.skilllink.domain.learning.project.service.ProjectService;
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

@RestController
@RequestMapping("/projects")
@Tag(name = "Gestión de proyectos", description = "Endpoints para el modulo de gestios de proyectos.")
@SecurityRequirement(name = "Bearer Authentication")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectContributionService contributionService;

    public ProjectController(ProjectService projectService, ProjectContributionService contributionService) {
        this.projectService = projectService;
        this.contributionService = contributionService;
    }

    @GetMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Page<ProjectPreviewDTO>> getAllProjectByMentor(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(projectService.getAllEnabledProjectByMentor(pageable));
    }

    @GetMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ProjectDetailDTO> getProjectByIdForMentor(@PathVariable Long id) {
        ProjectDetailDTO project = projectService.getEnabledProjectByIdForMentor(id);
        return ResponseEntity.ok(project);
    }

    // crear Proyectos
    @PostMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ProjectPreviewDTO> createProject(@Valid @RequestBody ProjectCreateDTO dto) {
        ProjectPreviewDTO created = projectService.createProjectByMentor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Editar Proyectos
    @PutMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ProjectDetailDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectUpdateDTO dto) {
        return ResponseEntity.ok(projectService.updateProjectByMentor(id, dto));
    }
    //Deshabilitar Proyectos
    @DeleteMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.disableProjectByMentor(id);
        return ResponseEntity.noContent().build();
    }

    //<---- Rutas para acceder cualquier usuario logueado ----->

    //Obtener Proyectos pot ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    //Obtener todos los Proyectos
    @GetMapping
    public ResponseEntity<Page<ProjectPreviewDTO>> getAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(projectService.getAllActiveProjects(pageable));
    }

    @PatchMapping("/mentor/{id}/status")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Void> changeProjectStatus(
            @PathVariable Long id,
            @Valid @RequestBody ProjectStatusUpdateDTO statusDTO
    ) {
        projectService.changeProjectStatus(id, statusDTO.status());
        return ResponseEntity.noContent().build();
    }

    //las contribuciones solos la deberan hacer os usuarios que esten enrolados o el creador del proyecto
    @PostMapping("/{id}/contributions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> addContribution(
            @PathVariable Long id,
            @Valid @RequestBody ProjectContributionCreateDTO dto
    ){
        contributionService.addContribution(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
