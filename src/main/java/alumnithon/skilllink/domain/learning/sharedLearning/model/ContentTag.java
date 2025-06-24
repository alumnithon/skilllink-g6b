package alumnithon.skilllink.domain.learning.sharedLearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "content_tags", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"content_type", "content_id", "tag_id"})
})
public class ContentTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private Long contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public ContentTag() {
    }

    public ContentTag(ContentType contentType, Long contentId, Tag tag) {
        this.contentType= contentType;
        this.contentId = contentId;
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }
}

