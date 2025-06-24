package alumnithon.skilllink.domain.learning.project.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.project.dto.ProjectContributionCreateDTO;
import alumnithon.skilllink.domain.learning.project.mapper.ProjectContributionMapper;
import alumnithon.skilllink.domain.learning.project.model.Project;
import alumnithon.skilllink.domain.learning.project.model.ProjectContribution;
import alumnithon.skilllink.domain.learning.project.repository.ProjectContributionRepository;
import alumnithon.skilllink.domain.learning.project.validator.ValidateProjectByID;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectContributionService {

    private final ValidateProjectByID validateProjectByID;
    private final AuthenticatedUserProvider userProvider;
    private final ProjectContributionRepository projectContributionRepository;

    public ProjectContributionService(ValidateProjectByID validateProjectByID, AuthenticatedUserProvider userProvider, ProjectContributionRepository projectContributionRepository) {
        this.validateProjectByID = validateProjectByID;
        this.userProvider = userProvider;
        this.projectContributionRepository = projectContributionRepository;
    }


    //TODO:estas contribuciones solos la deberan hacer os usuarios que esten enrolados o el creador del proyecto
    @Transactional
    public void addContribution(Long projectId, ProjectContributionCreateDTO dto) {
        Project project = validateProjectByID.validateExistsAndEnabled(projectId);
        User currentUser = userProvider.getCurrentUser();

        ProjectContribution contribution = ProjectContributionMapper.toEntity(dto, currentUser, project);

        projectContributionRepository.save(contribution);
    }
}
