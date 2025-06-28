package alumnithon.skilllink.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import alumnithon.skilllink.domain.userprofile.model.Certification;
import alumnithon.skilllink.domain.userprofile.model.Country;
import alumnithon.skilllink.domain.userprofile.model.Profile;
import alumnithon.skilllink.domain.userprofile.model.ProfileVisibility;
import alumnithon.skilllink.domain.userprofile.model.User;

public class ProfileTest {
    @Test
    void testProfileEntity() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Crear un objeto Country válido
        Country country = new Country();
        country.setId(1); // Usando Integer como decidiste

        Profile profile = new Profile();
        profile.setId(1L);
        profile.setUser(user);
        profile.setBio("Test bio");
        profile.setLocation("Test location");
        profile.setOccupation("Test occupation");
        profile.setExperience("Test experience");
        profile.setVisibility(ProfileVisibility.PUBLIC);
        profile.getSkills().add("Java");
        profile.getInterests().add("Programming");
        profile.getSocialLinks().put("GitHub", "https://github.com/test");
        profile.setContactEmail("contact@test.com");
        profile.setContactPhone("123456789");
        profile.setCountry(country); // Pasar el objeto Country, no un int
        Certification certification = new Certification();
        certification.setTitle("Oracle Certified");
        certification.setUrl("https://example.com/cert");
        
        profile.getCertifications().add(certification);

        // Act & Assert
        assertEquals(1L, profile.getId());
        assertEquals(user, profile.getUser());
        assertEquals("Test bio", profile.getBio());
        assertEquals("Test location", profile.getLocation());
        assertEquals("Test occupation", profile.getOccupation());
        assertEquals("Test experience", profile.getExperience());
        assertEquals(ProfileVisibility.PUBLIC, profile.getVisibility());
        assertEquals(1, profile.getSkills().size());
        assertEquals(1, profile.getInterests().size());
        assertEquals(1, profile.getSocialLinks().size());
        assertEquals("contact@test.com", profile.getContactEmail());
        assertEquals("123456789", profile.getContactPhone());
        assertEquals(country, profile.getCountry()); // Comparar con el objeto Country
        assertEquals(1, profile.getCertifications().size());
        assertEquals("Oracle Certified", profile.getCertifications().get(0).getTitle());
        assertNotNull(profile.getCreatedAt());
    }
    @Test
void testCountryRelationship() {
    Country country = new Country();
    country.setId(1);
    
    Profile profile = new Profile();
    profile.setCountry(country);
    
    assertSame(country, profile.getCountry());
    assertEquals(1, profile.getCountry().getId());
}
}
