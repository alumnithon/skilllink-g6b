package alumnithon.skilllink.domain.learning.project.repository;

import alumnithon.skilllink.domain.learning.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    @Query("SELECT COUNT(p.id) > 0 FROM Project p WHERE LOWER(p.title) = LOWER(:title) AND p.createdBy.id = :createdById")
    boolean existsByTitleAndCreatedById(@Param("title") String title, @Param("createdById") Long createdById);

}