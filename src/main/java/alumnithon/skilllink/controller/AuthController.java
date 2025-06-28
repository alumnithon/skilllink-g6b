package alumnithon.skilllink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alumnithon.skilllink.domain.auth.dto.AuthRequestDTO;
import alumnithon.skilllink.domain.auth.dto.AuthResponseDTO;
import alumnithon.skilllink.domain.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autentificar usuario con email y contraseña.")
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> authenticateUser(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.authenticateUser(authRequestDTO));
    }

}
