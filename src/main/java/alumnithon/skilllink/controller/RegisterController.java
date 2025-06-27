package alumnithon.skilllink.controller;

import java.util.Map;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import alumnithon.skilllink.domain.userprofile.dto.RegisterRequestDto;
import alumnithon.skilllink.domain.userprofile.service.RegisterService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
@Tag(name = "Registrar Usuario", description = "Entpoints para registrar un usuario")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid RegisterRequestDto usuario) {
        registerService.createUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registred correctly");
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token) {
        registerService.confirmAndActivateUser(token);
        return ResponseEntity.status(HttpStatus.FOUND).body("Cuenta activada correctamente");
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerification(@RequestParam String email) {
        registerService.resendVerificationToken(email);
        return ResponseEntity.ok(Map.of(
                "message", "Correo de verificación reenviado"));
    }

}
