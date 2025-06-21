package alumnithon.skilllink.domain.userprofile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequestDto {

    public RegisterRequestDto(String string, String string2, String string3, String string4) {
        this.name = string;
        this.email = string2;
        this.password = string3;
        this.image_url = string4;
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

    //---Setter---

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public void setEmail(@Email(message = "The email must be in a valid format") @NotBlank(message = "Email is required") String email) {
        this.email = email;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Must include uppercase, lowercase, number, and special character") String password) {
        this.password = password;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
