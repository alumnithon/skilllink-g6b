package alumnithon.skilllink.domain.learning.challenge.repository;

import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Optional<Challenge> findByIdAndEnabledTrueAndCreatedBy_Id(Long id, Long createdBy_Id);
    Page<Challenge> findByCreatedByIdAndEnabledTrue(Long mentorId, Pageable pageable);

    @Query("SELECT COUNT(c) > 0 FROM Challenge c WHERE c.title = :title AND c.enabled = true AND c.createdBy.id = :createdById")
    boolean existsByTitleAndEnabledAndCreatedBy(@Param("title") String title, @Param("createdById") Long createdById);
}
