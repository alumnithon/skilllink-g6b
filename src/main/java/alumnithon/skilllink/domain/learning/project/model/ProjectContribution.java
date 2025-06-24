package alumnithon.skilllink.domain.learning.project.model;

import alumnithon.skilllink.domain.userprofile.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_contributions")
@AllArgsConstructor
public class ProjectContribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "contribution_type", nullable = false)
    private ContributionType contributionType = ContributionType.TASK;

    @Column(name = "progress_contributed", nullable = false)
    private Integer progressContributed = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public ProjectContribution() {
    }

    public ProjectContribution(Project project, User userBy, String description, ContributionType contributionType, Integer progressContributed) {
        this.project = project;
        this.user = userBy;
        this.description = description;
        this.contributionType = contributionType;
        this.increaseProgress(progressContributed);
    }

    public void increaseProgress(int additionalProgress) {
        if (additionalProgress <= 0) return;

        int newProgress = this.progressContributed + additionalProgress;
        this.progressContributed = Math.min(newProgress, 100);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public ContributionType getContributionType() {
        return contributionType;
    }

    public Integer getProgressContributed() {
        return progressContributed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}