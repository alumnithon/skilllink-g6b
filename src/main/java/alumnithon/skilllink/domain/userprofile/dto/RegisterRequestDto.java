package alumnithon.skilllink.domain.userprofile.dto;

import jakarta.validation.constraints.*;
import lombok.Setter;
@Setter
public class RegisterRequestDto {

    public RegisterRequestDto(String name, String email, String password, String image_url) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image_url = image_url;
    }
    
    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "The email must be in a valid format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Must include uppercase, lowercase, number, and special character")
    private String password;
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN|ROLE_MENTOR)$", message= "Invalid role")
    private String role;
    private String image_url;

    //---Getter---

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public @Email(message = "The email must be in a valid format") @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Must include uppercase, lowercase, number, and special character") String getPassword() {
        return password;
    }

    public String getImage_url() {
        return image_url;
    }

    public @NotBlank(message = "Role is required") @Pattern(regexp = "^(ROLE_USER|ROLE_ADMIN|ROLE_MENTOR)$", message = "Invalid role") String getRole() {
        return role;
    }

}
