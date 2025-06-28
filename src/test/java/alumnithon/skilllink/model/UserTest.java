package alumnithon.skilllink.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;

public class UserTest {
@Test
    void testUserEntity() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ROLE_USER);
        user.setImage_url("/images/test.jpg");
        user.setEnabled(true);

        // Act & Assert
        assertEquals(1L, user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(Role.ROLE_USER, user.getRole());
        assertEquals("/images/test.jpg", user.getImage_url());
        assertTrue(user.getEnabled());
        assertNotNull(user.getCreatedAt());
        assertNull(user.getProfile()); // Al principio no tiene perfil
    }

    @Test
    void testUserDetailsMethods() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ROLE_USER);
        user.setEnabled(true);

        // Act & Assert
        assertEquals("test@example.com", user.getUsername());
        assertEquals("encodedPassword", user.getPassword());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
        assertEquals(1, user.getAuthorities().size());
        assertEquals("ROLE_USER", user.getAuthorities().iterator().next().getAuthority());
    }
}
