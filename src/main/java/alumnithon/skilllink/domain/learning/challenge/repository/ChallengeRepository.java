package alumnithon.skilllink.domain.learning.challenge.repository;

import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Page<Challenge> findAllByEnabledTrue( Pageable pageable);
    Optional<Challenge> findByIdAndEnabledTrue(Long id);
    Optional<Challenge> findByIdAndEnabledTrueAndCreatedBy_Id(Long id, Long createdBy_Id);
    Page<Challenge> findByCreatedByIdAndEnabledTrue(Long mentorId, Pageable pageable);

    @Query("SELECT COUNT(c.id) > 0 FROM Challenge c WHERE LOWER(c.title) = LOWER(:title) AND c.enabled = true AND c.createdBy.id = :createdById")
    boolean existsByTitleAndEnabledAndCreatedBy(@Param("title") String title, @Param("createdById") Long createdById);
}
