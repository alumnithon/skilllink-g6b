package alumnithon.skilllink.domain.userprofile.dto;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public class UpdateProfileDto {
    @Size(max = 500, message = "The maximum length of bio is 500 characters")
    private String bio;
    private String location;
    private String ocupation;
    private String experience;
    private String visibility;
    private List<String> skills;
    private List<String> interests;
    @Schema(description = "Redes sociales permitidas: linkedin, github, twitter, instagram, facebook, portfolio", example = "{\"linkedin\":\"https://linkedin.com/user\", \"github\":\"https://github.com/user\"}")
    private Map<String, String> socialLinks;
    private String contactEmail;
    private String contactPhone;
    private Integer countryId;
    private List<String> certifications;
    private String image_url;
    private String name;
    private String password;
    // ---Getter---
    public @Size(max = 500, message = "The maximum length of bio is 500 characters") String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getOcupation() {
        return ocupation;
    }

    public String getExperience() {
        return experience;
    }

    public String getVisibility() {
        return visibility;
    }

    public List<String> getSkills() {
        return skills;
    }

    public List<String> getInterests() {
        return interests;
    }

    public Map<String, String> getSocialLinks() {
        return socialLinks;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public List<String> getCertifications() {
        return certifications;
    }

    public String getImageUrl(){
        return image_url;
    }
    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    //---Setter---

    public void setBio(@Size(max = 500, message = "The maximum length of bio is 500 characters") String bio) {
        this.bio = bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setVisivility(String visivility) {
        visibility = visivility;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public void setSocialLinks(Map<String, String> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }
    public void setImageUrl(String image_url){
        this.image_url = image_url;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


