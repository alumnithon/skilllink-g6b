package alumnithon.skilllink.domain.userprofile.dto;

public class UserDto {

    private String name;
    private String email;
    private String role;
    private String image_url;
    private Boolean enabled;

//  ---Getter---
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getImage_url() {
        return image_url;
    }

    public Boolean getEnabled() {
        return enabled;
    }
 //---Setter---

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
