package alumnithon.skilllink.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import alumnithon.skilllink.domain.userprofile.model.Profile;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.ProfileRepository;

@ExtendWith(MockitoExtension.class)
public class ProfileRepositoryTest {
  @Mock
    private ProfileRepository profileRepository;

    @Test
    public void testFindByUser() {
        // 1. Preparar datos de prueba
        User user = new User();
        user.setId(1L);
        
        Profile expectedProfile = new Profile();
        expectedProfile.setId(1L);
        expectedProfile.setUser(user);

        // 2. Configurar el comportamiento del mock
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(expectedProfile));

        // 3. Ejecutar el método bajo prueba
        Optional<Profile> result = profileRepository.findByUser(user);

       // 4. Verificar los resultados
    assertTrue(result.isPresent(), "El perfil debería estar presente");
    Profile actualProfile = result.get();
    
    assertEquals(expectedProfile.getId(), actualProfile.getId(), "Los IDs de perfil deberían coincidir");
    assertEquals(user, actualProfile.getUser(), "Los usuarios deberían coincidir");
    
        // 5. Verificar interacciones con el mock
        verify(profileRepository, times(1)).findByUser(user);
    }
}