package alumnithon.skilllink.domain.userprofile.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;

public class RegistrerProfileDto {
    @Size(max = 500, message = "The maximum length of bio is 500 characters")
    private String bio;
    private String location;
    private String ocupation;
    private String experience;
    private String visibility;
    private List<String> skills;
    private List<String> interests;
    private Map<String, String> socialLinks;
    private String contactEmail;
    private String contactPhone;
    private Long countryId;
    private List<String> certifications;


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

    public Long getCountryId() {
        return countryId;
    }

    public List<String> getCertifications() {
        return certifications;
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

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }
}
