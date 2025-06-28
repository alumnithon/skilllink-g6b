package alumnithon.skilllink.domain.learning.course.repository;

import alumnithon.skilllink.domain.learning.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByEnabledTrue();
    Optional<Course> findByIdAndEnabledTrue(Long id);

    Page<Course> findByCreatedByIdAndEnabledTrue(Long id, Pageable pageable);
    Optional<Course> findByIdAndEnabledTrueAndCreatedBy_Id(Long id, Long id1);

    @Query("SELECT COUNT(c.id) > 0 FROM Course c WHERE LOWER(c.title) = LOWER(:title) AND c.createdBy.id = :createdById")
    boolean existsByTitleAndCreatedBy(@Param("title") String title, @Param("createdById") Long createdById);

}
