package alumnithon.skilllink.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import alumnithon.skilllink.domain.userprofile.model.*;
import alumnithon.skilllink.domain.userprofile.dto.RegisterRequestDto;
import alumnithon.skilllink.domain.userprofile.model.VerificationToken;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.domain.userprofile.repository.VerificationTokenRepository;
import alumnithon.skilllink.domain.userprofile.service.RegisterService;
import alumnithon.skilllink.domain.userprofile.service.SendMailService;
import alumnithon.skilllink.shared.exception.AppException;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @Mock
    private SendMailService sendMailService;

    @InjectMocks
    private RegisterService registerService;

    private RegisterRequestDto createValidRegisterDto() {
        return new RegisterRequestDto(
                "Test User",
                "test@example.com",
                "ValidPass123!",
                null);
    }

    @Test
    void createUser_ShouldSuccess() {
        // Arrange
        RegisterRequestDto dto = createValidRegisterDto();

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("ValidPass123!")).thenReturn("encodedPassword");

        // Act
        registerService.createUser(dto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("Test User", savedUser.getName());
        assertNotNull(savedUser.getRole()); // El role debería asignarse en el servicio
        assertTrue(savedUser.getEnabled()); // Asignado automáticamente
        assertNotNull(savedUser.getImage_url()); // Asignado automáticamente
    }

    @Test
    void createUser_ShouldThrowWhenEmailExists() {
        // Arrange
        RegisterRequestDto dto = createValidRegisterDto();

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(AppException.class, () -> registerService.createUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldHandleImageUrl() {
        // Arrange
        RegisterRequestDto dto = new RegisterRequestDto(
                "Test User",
                "test@example.com",
                "ValidPass123!",
                "custom-image.jpg");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("ValidPass123!")).thenReturn("encodedPassword");

        // Act
        registerService.createUser(dto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        assertEquals("custom-image.jpg", userCaptor.getValue().getImage_url());
    }

    @Test
    void confirmToken_ShouldSuccess() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setEnabled(false);

        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusHours(24));

        when(tokenRepository.findByToken(anyString())).thenReturn(token);

        // Act
        VerificationToken result = registerService.confirmToken("validToken");

        // Assert
        assertNotNull(result);
        verify(userRepository).save(user);
        assertTrue(user.getEnabled());
    }

    @Test
    void resendVerificationToken_ShouldSuccess() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setEnabled(false);

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Act
        registerService.resendVerificationToken("test@example.com");

        // Assert
        verify(tokenRepository).deleteByUser(user);
        verify(sendMailService).sendVerificationEmail(eq("test@example.com"), anyString());
    }
}