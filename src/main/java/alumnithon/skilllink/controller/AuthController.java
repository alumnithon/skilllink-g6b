package alumnithon.skilllink.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Game Sessions", description = "Endpoints probar.")
public class AuthController {
    @GetMapping("/ping")
    public String ping() {
        return "SkillLink API is running 🚀";
    }

}
