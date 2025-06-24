package alumnithon.skilllink.domain.userprofile.dto;

public class UserPrivateDto {
    String name;
    String image_url;

    // ---Getter---
    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    // ---Setter---
    public void setName(String name) {
        this.name = name;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
