package alumnithon.skilllink.domain.learning.challenge.model;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeUpdateDto;
import alumnithon.skilllink.domain.learning.sharedLearning.interfaces.OwnableByMentor;
import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;
import alumnithon.skilllink.domain.userprofile.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "challenges")
@AllArgsConstructor
@Builder
public class Challenge implements OwnableByMentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false, length = 15)
    private DifficultyLevel difficultyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    public Challenge() {
    }

    public Challenge(String title, String description, DifficultyLevel difficultyLevel, LocalDate deadline, User creator) {
        this.title = title;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.deadline = deadline;
        this.createdBy = creator;
    }

    public void updateFromDto(ChallengeUpdateDto dto) {
        if (dto.title() != null && !dto.title().isEmpty()) this.title = dto.title();
        if (dto.description() != null && !dto.description().isEmpty()) this.description = dto.description();
        if (dto.difficultyLevel() != null)  this.difficultyLevel = dto.difficultyLevel();
        if (dto.deadline() != null) this.deadline = dto.deadline();
    }

    public void disable() {
        this.enabled = false;
    }

    @Override
    public User getCreatedBy() {
        return this.createdBy;
    }


//<--  Getter -->

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
