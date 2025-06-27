package alumnithon.skilllink.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import alumnithon.skilllink.domain.userprofile.dto.RegisterRequestDto;
import alumnithon.skilllink.domain.userprofile.service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {
    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    private RegisterRequestDto createValidRequestDto() {
        RegisterRequestDto dto = new RegisterRequestDto(
                "Test User",
                "test@example.com",
                "Password123!",
                "/images/test.jpg");
        // El role se setea aparte porque no está en el constructor
        dto.setRole("ROLE_USER");
        return dto;
    }

    @Test
    void registerUser_ShouldReturnCreatedStatus() {
        // Arrange
        RegisterRequestDto requestDto = createValidRequestDto();

        doNothing().when(registerService).createUser(any(RegisterRequestDto.class));

        // Act
        ResponseEntity<?> response = registerController.createUser(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(registerService, times(1)).createUser(requestDto);
    }

    @Test
    void confirmToken_ShouldReturnOkStatus() {
        // Arrange
        String token = "valid-token";
        doNothing().when(registerService).confirmAndActivateUser(token);

        // Act
        ResponseEntity<?> response = registerController.confirmToken(token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(registerService, times(1)).confirmAndActivateUser(token);
    }
}