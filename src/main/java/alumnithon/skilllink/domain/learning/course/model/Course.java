package alumnithon.skilllink.domain.learning.course.model;


import alumnithon.skilllink.domain.learning.course.dto.CourseUpdateDTO;
import alumnithon.skilllink.domain.learning.sharedLearning.interfaces.OwnableByMentor;
import alumnithon.skilllink.domain.userprofile.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@Builder
public class Course implements OwnableByMentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "has_certification")
    private Boolean hasCertification = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean enabled = true;

    public Course() {
    }

    public Course(String title, String description, Boolean hasCertification, User creator) {
        this.title = title;
        this.description =description;
        this.hasCertification= hasCertification;
        this.createdBy = creator;
    }

    public void update(CourseUpdateDTO dto) {
        if (dto.title() != null && !dto.title().isEmpty()) this.title = dto.title();
        if (dto.description() != null && !dto.description().isEmpty()) this.description = dto.description();
        if (dto.hasCertification() != null) this.hasCertification = dto.hasCertification();
    }

    public void disable() {
        this.enabled = false;
    }

    @Override
    public User getCreatedBy() {
        return this.createdBy;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getHasCertification() {
        return hasCertification;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}