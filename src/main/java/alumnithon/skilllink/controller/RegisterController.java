    package alumnithon.skilllink.controller;

    import java.util.Map;

    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import alumnithon.skilllink.domain.userprofile.dto.RegisterRequestDto;
    import alumnithon.skilllink.domain.userprofile.service.RegisterService;
    import io.micrometer.core.instrument.config.validate.ValidationException;
    import jakarta.transaction.Transactional;
    import jakarta.validation.Valid;
    
    @RestController
    @RequestMapping("/register")
    @Tag(name = "Registrar Usuario", description = "Entpoints para registrar un usuario")
    public class RegisterController {
    
         
         private final RegisterService registerService;
         public RegisterController(RegisterService registerService){
            this.registerService = registerService;
         }
    
        @PostMapping 
        @Transactional
        public ResponseEntity<?> createUser(@RequestBody @Valid RegisterRequestDto usuario) {
             registerService.createUser(usuario);
             return ResponseEntity.status(HttpStatus.CREATED).body("User registred correctly");
        }
    }
    

