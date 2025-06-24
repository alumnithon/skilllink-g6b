package alumnithon.skilllink.domain.learning.project.repository;

import alumnithon.skilllink.domain.learning.project.model.ProjectContribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectContributionRepository extends JpaRepository<ProjectContribution, Long> {
}
