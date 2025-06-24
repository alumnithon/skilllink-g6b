package alumnithon.skilllink.domain.learning.project.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.course.validator.IsExistsCourseByTitle;
import alumnithon.skilllink.domain.learning.course.validator.ValidateCourseByID;
import alumnithon.skilllink.domain.learning.project.dto.ProjectCreateDTO;
import alumnithon.skilllink.domain.learning.project.dto.ProjectPreviewDTO;
import alumnithon.skilllink.domain.learning.project.mapper.ProjectMapper;
import alumnithon.skilllink.domain.learning.project.model.Project;
import alumnithon.skilllink.domain.learning.project.repository.ProjectContributionRepository;
import alumnithon.skilllink.domain.learning.project.repository.ProjectRepository;
import alumnithon.skilllink.domain.learning.project.validator.IsExistsProjectByTitle;
import alumnithon.skilllink.domain.learning.sharedLearning.dto.TagsToContentDTO;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.learning.sharedLearning.service.ContentTagService;
import alumnithon.skilllink.domain.learning.sharedLearning.validator.ValidatorCreatedBy;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectContributionRepository projectContributionRepository;
    private final AuthenticatedUserProvider userProvider;
    private final ProjectMapper projectMapper;
    private final ContentTagService contentTagService;
    private final IsExistsProjectByTitle isExistsProjectByTitle;

    public ProjectService(ProjectRepository projectRepository, ProjectContributionRepository projectContributionRepository, AuthenticatedUserProvider userProvider, ProjectMapper projectMapper, ContentTagService contentTagService, IsExistsProjectByTitle isExistsProjectByTitle) {
        this.projectRepository = projectRepository;
        this.projectContributionRepository = projectContributionRepository;
        this.userProvider = userProvider;
        this.projectMapper = projectMapper;
        this.contentTagService = contentTagService;
        this.isExistsProjectByTitle = isExistsProjectByTitle;
    }


    public ProjectPreviewDTO createProjectByMentor(@Valid ProjectCreateDTO dto) {
        var creator = userProvider.getCurrentUser();
        isExistsProjectByTitle.validatorsProject(dto,null, creator.getId());
        Project project = projectMapper.toEntity(dto, creator);
        projectRepository.save(project);

        // Agrega tags relacionados
        if (dto.tagsName() != null && !dto.tagsName().isEmpty()) {
            TagsToContentDTO tagsDTO = new TagsToContentDTO(
                    ContentType.PROJECT,
                    project.getId(),
                    dto.tagsName()
            );
            contentTagService.addTagsToContent(tagsDTO);
        }

        return projectMapper.toPreviewDTO(project);

    }
}
