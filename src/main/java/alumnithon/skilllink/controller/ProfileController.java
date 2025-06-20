package alumnithon.skilllink.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import alumnithon.skilllink.domain.userprofile.dto.RegisterProfileDto;
import alumnithon.skilllink.domain.userprofile.service.ProfileService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired 
    public ProfileService profileService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> CreateProfile(@RequestBody @Valid RegisterProfileDto registred){
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
}

