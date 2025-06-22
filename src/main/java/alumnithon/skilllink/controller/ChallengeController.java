package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.service.ChallengeService;
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
@RequestMapping("/challenges")
@Tag(name = "Gestión de Desafíos", description = "Endpoints para el modulo de desafíos.")
@SecurityRequirement(name = "Bearer Authentication")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    // Crear challenges
    @PostMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<ChallengePreviewDto> createChallenge(@Valid @RequestBody ChallengeCreateDto dto) {
        ChallengePreviewDto created = challengeService.createChallengeByMentor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}
