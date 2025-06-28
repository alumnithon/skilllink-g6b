package alumnithon.skilllink.domain.learning.project.model;

import alumnithon.skilllink.domain.learning.project.dto.ProjectUpdateDTO;
import alumnithon.skilllink.domain.learning.sharedLearning.interfaces.OwnableByMentor;
import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;
import alumnithon.skilllink.domain.userprofile.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@AllArgsConstructor
public class Project implements OwnableByMentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status = ProjectStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", nullable = false, length = 20)
    private DifficultyLevel difficultyLevel = DifficultyLevel.BEGINNER;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectContribution> contributions = new ArrayList<>();

    public Project() {
    }

    public Project( String title, String description, User createdBy, DifficultyLevel difficultyLevel, List<ProjectContribution> contributions  ) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.difficultyLevel = difficultyLevel;
        this.contributions = contributions;
    }

    public void disable() {
        this.status = ProjectStatus.ARCHIVED;
    }

    public void changeStatus(ProjectStatus newStatus) {
        this.status = newStatus;
    }

    @Override
    public User getCreatedBy() {
        return this.createdBy;
    }

    public void updateProject(ProjectUpdateDTO dto) {
        if (dto.title() != null && !dto.title().isBlank()) {
            this.title = dto.title();
        }
        if (dto.description() != null && !dto.description().isBlank()) {
            this.description = dto.description();
        }
        if (dto.difficultyLevel() != null) {
            this.difficultyLevel = dto.difficultyLevel();
        }
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ProjectContribution> getContributions() {
        return contributions;
    }
}
