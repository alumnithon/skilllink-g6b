package alumnithon.skilllink.domain.learning.sharedLearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    public Tag(Long id, String tagName) {
        this.id = id;
        this.name = tagName;
    }

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}