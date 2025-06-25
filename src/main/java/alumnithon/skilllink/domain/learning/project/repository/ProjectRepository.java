package alumnithon.skilllink.domain.learning.project.repository;

import alumnithon.skilllink.domain.learning.project.model.Project;
import alumnithon.skilllink.domain.learning.project.model.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    Optional<Project> findByIdAndStatusNot(Long id, ProjectStatus projectStatus);
    Page<Project> findByStatusNot(ProjectStatus projectStatus, Pageable pageable);

    Page<Project> findByCreatedByIdAndStatusNot(Long id, ProjectStatus projectStatus, Pageable pageable);
    Optional<Project> findByIdAndCreatedByIdAndStatusNot(Long id, Long id1, ProjectStatus projectStatus);

    @Query("SELECT COUNT(p.id) > 0 FROM Project p WHERE LOWER(p.title) = LOWER(:title) AND p.createdBy.id = :createdById")
    boolean existsByTitleAndCreatedById(@Param("title") String title, @Param("createdById") Long createdById);

}