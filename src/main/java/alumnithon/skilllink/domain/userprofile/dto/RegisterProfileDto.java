package alumnithon.skilllink.domain.userprofile.dto;

import java.util.List;
import java.util.Map;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterProfileDto {
  
    @Size(max = 500, message = "The maximum length of bio is 500 characters")
    private String bio;

    @NotBlank(message = "Ocupation is required")
    private String ocupation;

    @NotBlank(message = "Experience is required")
    private String experience;

    private List<String> skills;
    private List<String> interests;
    private Map<String, String> socialLinks;

    @NotNull 
    @Email(message= "The contact email must be in a valid format")
    private String contactEmail;
    
    private String contactPhone;

    @NotBlank(message="Location is required")
    @NotNull
    private Long countryId;

    private List<String> certifications;
}
