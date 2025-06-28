package alumnithon.skilllink.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import alumnithon.skilllink.domain.userprofile.dto.*;
import alumnithon.skilllink.domain.userprofile.model.Country;
import alumnithon.skilllink.domain.userprofile.model.Profile;
import alumnithon.skilllink.domain.userprofile.model.ProfileVisibility;
import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.CountryRepository;
import alumnithon.skilllink.domain.userprofile.repository.ProfileRepository;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.domain.userprofile.service.ProfileService;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void createProfile_ShouldSuccess() {
        // Arrange
        RegisterProfileDto dto = new RegisterProfileDto();
        dto.setCountryId(1);
        User user = new User();
        user.setId(1L);
        user.setRole(Role.ROLE_USER);

        Country country = new Country();
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(countryRepository.findById(1)).thenReturn(Optional.of(country));

        // Act
        profileService.Create(dto);

        // Assert
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    void getProfile_ShouldReturnProfile() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Profile profile = new Profile();
        profile.setUser(user);

        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile)); 

        // Act
        GetProfileDto result = profileService.GetProfile();

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getUser().getName());
    }

    @Test
    void updateProfile_ShouldUpdateFields() {
        // Arrange
        UpdateProfileDto dto = new UpdateProfileDto();
        dto.setBio("New bio");
        User user = new User();
        Profile profile = new Profile();

        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile)); 

        // Act
        profileService.Update(dto);

        // Assert
        assertEquals("New bio", profile.getBio());
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void deleteProfile_ShouldDelete() {
        // Arrange
        User user = new User();
        Profile profile = new Profile();

        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile)); 

        // Act
        profileService.Delete();

        // Assert
        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    void getProfileById_Public_ShouldReturnFullProfile() {
        // Arrange
        User user = new User();
        Profile profile = new Profile();
        profile.setVisibility(ProfileVisibility.PUBLIC);

        when(userRepository.findByIdAndEnabledTrue(1L)).thenReturn(user);
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        // Act
        Object result = profileService.GetProfileById(1L);

        // Assert
        assertTrue(result instanceof GetProfileDto);
    }
}
