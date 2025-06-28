package alumnithon.skilllink.domain.userprofile.dto;

import jakarta.validation.constraints.*;
import java.util.Map;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public class RegisterProfileDto {
    @Size(max = 500, message = "The maximum length of bio is 500 characters")
    private String bio;
    private String location;
    private String occupation;
    private String experience;
    @Schema(description = "Visibility of the profile: public, private", example = "public")
    @Pattern(regexp = "^(public|private)$", message = "Visibility must be one of: public, private, or restricted")
    private String visibility;
    private List<String> skills;
    private List<String> interests;
    @Schema(description = "Redes sociales permitidas: linkedin, github, twitter, instagram, facebook, portfolio", example = "{\"linkedin\":\"https://linkedin.com/user\", \"github\":\"https://github.com/user\"}")
    private Map<String, String> socialLinks;
    private String contactEmail;
    private String contactPhone;
    private Integer countryId;
    private List<CertificationDto> certifications;
  


    // --- Getters Corregidos ---
    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getOccupation() { // Nombre corregido
        return occupation;
    }

    public String getExperience() {
        return experience;
    }

    public String getVisibility() { // Nombre correcto
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

    public List<CertificationDto> getCertifications() {
        return certifications;
    }

    // --- Setters---
    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOccupation(String occupation) { // Nombre corregido
        this.occupation = occupation;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setVisibility(String visibility) { // Setter CORRECTO
        this.visibility = visibility.toUpperCase();
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

    public void setCertifications(List<CertificationDto> certifications) {
        this.certifications = certifications;
    }
}
