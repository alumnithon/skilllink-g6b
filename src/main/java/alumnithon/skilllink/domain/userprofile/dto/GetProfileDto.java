package alumnithon.skilllink.domain.userprofile.dto;

import java.util.List;
import java.util.Map;

public class GetProfileDto {
   private UserDto user;
   private String bio;
   private String location;
   private String occupation;
   private String experience;
   private String visibility;
   private List<String> skills;
   private List<String>interests;
   private Map<String, String> socialLinks;
   private String contactEmail;
   private String contactPhone;
   private CountryDto country;
   private List<CertificationDto> certifications;

   //---Getter---

   public UserDto getUser() {
      return user;
   }

   public String getBio() {
      return bio;
   }

   public String getLocation() {
      return location;
   }

   public String getOccupation() {
      return occupation;
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

   public CountryDto getCountry() {
      return country;
   }

   public List<CertificationDto> getCertifications() {
      return certifications;
   }

   //---Setter---

   public void setUser(UserDto user) {
      this.user = user;
   }

   public void setBio(String bio) {
      this.bio = bio;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public void setOccupation(String occupation) {
      this.occupation = occupation;
   }

   public void setExperience(String experience) {
      this.experience = experience;
   }

   public void setVisibility(String visibility) {
      this.visibility = visibility;
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

   public void setCountry(CountryDto country) {
      this.country = country;
   }

   public void setCertifications(List<CertificationDto> certifications) {
      this.certifications = certifications;
   }
}
