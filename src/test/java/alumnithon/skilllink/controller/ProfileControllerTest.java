package alumnithon.skilllink.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import alumnithon.skilllink.domain.userprofile.dto.GetProfileDto;
import alumnithon.skilllink.domain.userprofile.dto.RegisterProfileDto;
import alumnithon.skilllink.domain.userprofile.dto.UpdateProfileDto;
import alumnithon.skilllink.domain.userprofile.service.ProfileService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {
@Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    @Test
    void createProfile_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        RegisterProfileDto profileDto = new RegisterProfileDto();
        doNothing().when(profileService).Create(any(RegisterProfileDto.class));

        // Act
        ResponseEntity<?> response = profileController.CreateProfile(profileDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(profileService, times(1)).Create(profileDto);
    }

    @Test
    void getProfile_ShouldReturnOkStatus() throws Exception {
        // Arrange
        GetProfileDto expectedProfile = new GetProfileDto();
        when(profileService.GetProfile()).thenReturn(expectedProfile);

        // Act
        ResponseEntity<?> response = profileController.GetProfile();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProfile, response.getBody());
    }

    @Test
    void updateProfile_ShouldReturnOkStatus() throws Exception {
        // Arrange
        UpdateProfileDto updateDto = new UpdateProfileDto();
        doNothing().when(profileService).Update(any(UpdateProfileDto.class));

        // Act
        ResponseEntity<?> response = profileController.UpdateProfile(updateDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(profileService, times(1)).Update(updateDto);
    }
}
