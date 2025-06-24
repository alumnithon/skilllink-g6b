package alumnithon.skilllink.domain.learning.project.mapper;

import alumnithon.skilllink.domain.learning.project.dto.ProjectCreateDTO;
import alumnithon.skilllink.domain.learning.project.dto.ProjectPreviewDTO;
import alumnithon.skilllink.domain.learning.project.model.Project;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.learning.sharedLearning.service.ContentTagService;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMapper {
    private final ContentTagService contentTagService;

    public ProjectMapper(ContentTagService contentTagService) {
        this.contentTagService = contentTagService;
    }

    public Project toEntity(ProjectCreateDTO dto, User createdBy) {
        return new Project(
                dto.title(),
                dto.description(),
                createdBy,
                dto.difficultyLevel(),
                null
        );
    }

    public ProjectPreviewDTO toPreviewDTO(Project project) {
        List<String> tagNames = contentTagService.getTagNamesByContent(ContentType.PROJECT, project.getId());

        return new ProjectPreviewDTO(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getStatus().name(),
                project.getDifficultyLevel().name(),
                project.getCreatedBy().getName(),
                project.getCreatedAt(),
                tagNames
        );
    }
}
