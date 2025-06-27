package alumnithon.skilllink.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
        when(profileRepository.findByUser(user)).thenReturn(expectedProfile);

        // 3. Ejecutar el método bajo prueba
        Profile result = profileRepository.findByUser(user);

        // 4. Verificar los resultados
        assertNotNull(result, "El perfil no debería ser nulo");
        assertEquals(expectedProfile.getId(), result.getId(), "Los IDs de perfil deberían coincidir");
        assertEquals(user, result.getUser(), "Los usuarios deberían coincidir");
        
        // 5. Verificar interacciones con el mock
        verify(profileRepository, times(1)).findByUser(user);
    }
}