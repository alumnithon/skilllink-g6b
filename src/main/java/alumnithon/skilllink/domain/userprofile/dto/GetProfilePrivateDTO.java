package alumnithon.skilllink.domain.userprofile.dto;

import java.util.List;

public class GetProfilePrivateDTO {
    private UserPrivateDto user;
    private String bio;
    private List<String> Skills;
    private List<String> interest;
    private String country;

    // ---Getter---
    public UserPrivateDto getUser() {
        return user;
    }

    public String getBio() {
        return bio;
    }

    public List<String> getSkills() {
        return Skills;
    }

    public List<String> getInterest() {
        return interest;
    }

    public String getCountry() {
        return country;
    }

    // ---Setter--
    public void setUser(UserPrivateDto user) {
        this.user = user;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setSkills(List<String> skills) {
        Skills = skills;
    }

    public void setInterest(List<String> interest) {
        this.interest = interest;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
