package alumnithon.skilllink.domain.challenge.repository;

import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import alumnithon.skilllink.domain.learning.challenge.repository.ChallengeRepository;
import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;
import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
@Rollback
public class ChallengeRespositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    ChallengeRepository challengeRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = registerUser("user 1","mentor@test.com", String.valueOf(Role.ROLE_MENTOR));
        user2 = registerUser("user 2", "mentor2@test.com", String.valueOf(Role.ROLE_MENTOR));
    }

    @Test
    @DisplayName("Debe retornar true si existe un reto con título igual (ignorando mayúsculas) del mismo creador, enabled=true y creador específico")
    void existsByTitleAndCreatedBy_returnsTrueWhenExists() {
        //given o arrange
        registerChallenge("Reto Unico", user1);

        em.flush();
        em.clear();

        //when o act
        boolean exists = challengeRepository.existsByTitleAndCreatedBy("Reto Unico", user1.getId());

        //then o assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Debe retornar false si el título no coincide")
    void existsByTitleAndCreatedBy_returnsFalseWhenTitleDoesNotMatch() {
        // Given o arrange
        registerChallenge("Reto 1", user1);

        em.flush();
        em.clear();

        // When o Act
        boolean exists = challengeRepository.existsByTitleAndCreatedBy("titulo diferente", user1.getId());

        // Then o Assert
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Debe retornar false si el titulo con igual texto no es del mismo creador")
    void existsByTitleAndCreatedBy_returnsFalseWhenDisabled() {
        // Given o arrange
        registerChallenge("Reto 2", user1);

        em.flush();
        em.clear();

        // When o Act
        boolean exists = challengeRepository.existsByTitleAndCreatedBy("Reto 2", user2.getId());

        // Then o Assert
        assertThat(exists).isFalse();
    }

    private User registerUser(String name, String email, String role) {
        User user = new User();
        user.setName(name);
        user.setPassword("123456");
        user.setEmail(email);
        user.setRole(Role.valueOf(role));

        em.persist(user);
        return user;
    }

    private Challenge registerChallenge(String title, User creator) {
        var challenge = new Challenge(
                title,
                "Descripcion Challenge",
                DifficultyLevel.BEGINNER,
                LocalDate.now(),
                creator);
        em.persist(challenge);
        return challenge;
    }


}
