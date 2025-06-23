package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeDetailDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeUpdateDto;
import alumnithon.skilllink.domain.learning.challenge.service.ChallengeService;
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
@RequestMapping("/challenges")
@Tag(name = "Gestión de Desafíos", description = "Endpoints para el modulo de desafíos.")
@SecurityRequirement(name = "Bearer Authentication")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    //Obtener Challenger por ID del Mentor
    @GetMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ChallengeDetailDto> getByIdByMentor(@PathVariable Long id) {
        return ResponseEntity.ok(challengeService.getChallengeByIdForMentor(id));
    }

    //Obtener todos los Challenges
    @GetMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Page<ChallengePreviewDto>> getAllbyMentor(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(challengeService.getAllChallengesForMentor(pageable));
    }

    // Crear challenges
    @PostMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ChallengePreviewDto> createChallenge(@Valid @RequestBody ChallengeCreateDto dto) {
        ChallengePreviewDto created = challengeService.createChallengeByMentor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ChallengeDetailDto> update( @PathVariable Long id, @Valid @RequestBody ChallengeUpdateDto dto) {
        return ResponseEntity.ok(challengeService.updateChallengeByMentor(id, dto));
    }

    @DeleteMapping("/mentor/{id}")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        challengeService.deleteChallengeByMentor(id);
        return ResponseEntity.noContent().build();
    }


    //<---- Rutas para todos los los usuarios autenticados  ---->

    //Obtener Challenger por ID
    @GetMapping("/{id}")
    public ResponseEntity<ChallengeDetailDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(challengeService.getChallengeById(id));
    }

    //Obtener todos los Challenges
    @GetMapping
    public ResponseEntity<Page<ChallengePreviewDto>> getAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(challengeService.getAllChallenges(pageable));
    }

}
