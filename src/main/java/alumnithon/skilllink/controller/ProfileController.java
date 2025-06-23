package alumnithon.skilllink.controller;

import java.util.Map;

import alumnithon.skilllink.domain.userprofile.dto.RegistrerProfileDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import alumnithon.skilllink.domain.userprofile.service.ProfileService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/profile")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Gestión de Perfiles", description = "Endpoints para gestionar los perfiles de un usuario")
public class ProfileController {

    @Autowired 
    public ProfileService profileService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> CreateProfile(@RequestBody @Valid RegistrerProfileDto registred){
      try{
        profileService.Create( registred);
          return ResponseEntity.status(HttpStatus.CREATED).body("Profile registred correctly");
        }catch (ValidationException e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }catch(RuntimeException e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body(Map.of("error", e.getMessage()));    
        }
    }

    @GetMapping("/me")
    @Transactional
    public ResponseEntity<?> GetProfile(){
        try{
            return ResponseEntity.ok(profileService.GetProfile());
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
      }

      @PutMapping("/me")
      @Transactional
      public ResponseEntity<?> UpdateProfile(@RequestBody @Valid RegistrerProfileDto updated){
        try{
            profileService.Update(updated);
            return ResponseEntity.ok("Profile updated correctly");
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }

    }
  }