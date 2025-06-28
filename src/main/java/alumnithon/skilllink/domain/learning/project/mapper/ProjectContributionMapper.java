package alumnithon.skilllink.domain.learning.project.mapper;

import alumnithon.skilllink.domain.learning.project.dto.ContributionPreviewDTO;
import alumnithon.skilllink.domain.learning.project.dto.ProjectContributionCreateDTO;
import alumnithon.skilllink.domain.learning.project.model.Project;
import alumnithon.skilllink.domain.learning.project.model.ProjectContribution;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.stereotype.Component;

@Component
public class ProjectContributionMapper {
    public static ProjectContribution toEntity(ProjectContributionCreateDTO dto, User userBy, Project project) {
        return new ProjectContribution(
                project,
                userBy,
                dto.description(),
                dto.contributionType(),
                dto.progressContributed()
        );
    }

    public static ContributionPreviewDTO toContributionPreviewDTO(ProjectContribution contribution) {
        return new ContributionPreviewDTO(
                contribution.getId(),
                contribution.getUser().getName(),
                contribution.getDescription(),
                contribution.getContributionType().name(),
                contribution.getProgressContributed(),
                contribution.getCreatedAt()
        );
    }
}


