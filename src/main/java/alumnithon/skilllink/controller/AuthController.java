package alumnithon.skilllink.controller;

import alumnithon.skilllink.domain.auth.dto.AuthRequestDTO;
import alumnithon.skilllink.domain.auth.dto.AuthResponseDTO;
import alumnithon.skilllink.domain.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autentificar usuario con email y contraseña.")
public class AuthController {

    // ruta publica de prueba
    @GetMapping("/ping")
    public String ping() {
        return "SkillLink API is running 🚀";
    }

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> autenticateUser(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.authenticateUser(authRequestDTO));
    }

}
