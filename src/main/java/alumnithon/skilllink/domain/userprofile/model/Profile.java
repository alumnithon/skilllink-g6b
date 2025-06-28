package alumnithon.skilllink.domain.userprofile.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 100)
    private String location;

    @Column(length = 100)
    private String occupation;

    @Column(columnDefinition = "TEXT")
    private String experience;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ProfileVisibility visibility = ProfileVisibility.PUBLIC;

    // Habilidades técnicas
    @ElementCollection
    @CollectionTable(name = "profile_skills", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();

    // Intereses - Mapeo especial para tabla con PK en profile_id
    @ElementCollection
    @CollectionTable(name = "profile_interests", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "interest")
    private List<String> interests = new ArrayList<>();

    // Redes sociales
    @ElementCollection
    @CollectionTable(name = "profile_social_links", joinColumns = @JoinColumn(name = "profile_id"))
    @MapKeyColumn(name = "platform")
    @Column(name = "url")
    private Map<String, String> socialLinks = new HashMap<>();

    // Certificaciones - Mapeo especial para tabla con PK en profile_id
    @ElementCollection
    @CollectionTable(name = "profile_certifications", joinColumns = @JoinColumn(name = "profile_id"))
    private List<Certification> certifications = new ArrayList<>();

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    //---Getter---

    public Long getId() {
        return id;
    }

    public User getUser() {
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

    public ProfileVisibility getVisibility() {
        return visibility;
    }

    public List<String> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public List<String> getInterests() {
        return Collections.unmodifiableList(interests);
    }

    public Map<String, String> getSocialLinks() {
        return Collections.unmodifiableMap(socialLinks);
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Country getCountry() {
        return country;
    }

    public List<Certification> getCertifications() {
        return Collections.unmodifiableList(certifications);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //---Setter---


    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
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

    public void setVisibility(ProfileVisibility visibility) {
        this.visibility = visibility;
    }

    public void setSkills(List<String> skills) {
        this.skills.clear();
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }

    public void setInterests(List<String> interests) {
        this.interests.clear();
        if (interests != null) {
            this.interests.addAll(interests);
        }
    }
   
    public void setSocialLinks(Map<String, String> socialLinks) {
        this.socialLinks.clear();
        if (socialLinks != null) {
            this.socialLinks.putAll(socialLinks);
        }
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications.clear();
        if (certifications != null) {
            this.certifications.addAll(certifications);
        }
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    // --- MÉTODOS PARA MANIPULACIÓN CONTROLADA ---
    
    public void addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.skills.add(skill);
        }
    }

    public void removeSkill(String skill) {
        this.skills.remove(skill);
    }

    public void addInterest(String interest) {
        if (interest != null && !interest.trim().isEmpty()) {
            this.interests.add(interest);
        }
    }

    public void removeInterest(String interest) {
        this.interests.remove(interest);
    }

    public void addSocialLink(String platform, String url) {
        if (platform != null && url != null) {
            this.socialLinks.put(platform, url);
        }
    }

    public void removeSocialLink(String platform) {
        this.socialLinks.remove(platform);
    }

    public void addCertification(Certification certification) {
        if (certification != null) {
            this.certifications.add(certification);
        }
    }

    public void removeCertification(Certification certification) {
        this.certifications.remove(certification);
    }
}

