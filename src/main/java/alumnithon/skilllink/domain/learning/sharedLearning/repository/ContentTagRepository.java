package alumnithon.skilllink.domain.learning.sharedLearning.repository;

import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentTag;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {
    boolean existsByContentTypeAndContentIdAndTag_Id(ContentType type, Long contentId, Long tagId);

    List<ContentTag> findByContentTypeAndContentId(ContentType contentType, Long id);

}
