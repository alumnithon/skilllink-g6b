package alumnithon.skilllink.domain.learning.course.repository;

import alumnithon.skilllink.domain.learning.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByIdAndEnabledTrue(Long id);

    @Query("SELECT COUNT(c.id) > 0 FROM Course c WHERE LOWER(c.title) = LOWER(:title) AND c.enabled = true AND c.createdBy.id = :createdById")
    boolean existsByTitleAndEnabledAndCreatedBy(@Param("title") String title, @Param("createdById") Long createdById);

}
