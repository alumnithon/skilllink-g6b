package alumnithon.skilllink.domain.learning.enrollment.repository;

import alumnithon.skilllink.domain.learning.enrollment.model.Enrollment;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository  extends JpaRepository<Enrollment, Long> {
    boolean existsByUserAndContentTypeAndContentId(User user, ContentType type, Long contentId);

    List<Enrollment> findByUserId(Long id);
}

