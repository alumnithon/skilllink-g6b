package alumnithon.skilllink.domain.learning.sharedLearning.repository;

import alumnithon.skilllink.domain.learning.sharedLearning.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String tagName);
}

