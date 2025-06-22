package alumnithon.skilllink.domain.learning.challenge.repository;

import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Query("SELECT COUNT(c) > 0 FROM Challenge c WHERE c.title = :title AND c.enabled = true AND c.createdBy.id = :createdById")
    boolean existsByTitleAndEnabledAndCreatedBy(@Param("title") String title, @Param("createdById") Long createdById);
}
