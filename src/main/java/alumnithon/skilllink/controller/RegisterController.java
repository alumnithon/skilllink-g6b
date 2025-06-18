    package alumnithon.skilllink.controller;

    import java.util.Map;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import alumnithon.skilllink.domain.userprofile.dto.RegisterRequest;
    import alumnithon.skilllink.domain.userprofile.service.RegisterService;
    import io.micrometer.core.instrument.config.validate.ValidationException;
    import jakarta.transaction.Transactional;
    import jakarta.validation.Valid;
    
    @RestController
    @RequestMapping("/api/registrer")
    public class RegisterController {
    
         
         private final RegisterService registerService;
         public RegisterController(RegisterService registerService){
            this.registerService = registerService;
         }
    
        @PostMapping 
        @Transactional
        public ResponseEntity<?> crearUsuario(@RequestBody @Valid RegisterRequest usuario) {
            try {
               registerService.createUser(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registred correctly");
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", e.getMessage()));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Internal Server Error"));
            }
        }
    }
    

