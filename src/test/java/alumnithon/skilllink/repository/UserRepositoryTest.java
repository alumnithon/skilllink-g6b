package alumnithon.skilllink.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
 @Mock
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(expectedUser);

        // Act
        User result = userRepository.findByEmail("test@example.com");

        // Assert
        assertEquals(expectedUser, result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testExistsByEmail() {
        // Arrange
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act
        boolean exists = userRepository.existsByEmail("test@example.com");

        // Assert
        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    @Test
    void testFindByIdAndEnabledTrue() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setEnabled(true);
        when(userRepository.findByIdAndEnabledTrue(1L)).thenReturn(expectedUser);

        // Act
        User result = userRepository.findByIdAndEnabledTrue(1L);

        // Assert
        assertEquals(expectedUser, result);
        verify(userRepository, times(1)).findByIdAndEnabledTrue(1L);
    }
}
