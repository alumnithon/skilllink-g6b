package alumnithon.skilllink.domain.learning.challenge.repository;

import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
