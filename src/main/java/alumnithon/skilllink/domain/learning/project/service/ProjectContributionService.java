package alumnithon.skilllink.domain.learning.project.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.enrollment.repository.EnrollmentRepository;
import alumnithon.skilllink.domain.learning.project.dto.ProjectContributionCreateDTO;
import alumnithon.skilllink.domain.learning.project.mapper.ProjectContributionMapper;
import alumnithon.skilllink.domain.learning.project.model.Project;
import alumnithon.skilllink.domain.learning.project.model.ProjectContribution;
import alumnithon.skilllink.domain.learning.project.repository.ProjectContributionRepository;
import alumnithon.skilllink.domain.learning.project.validator.ValidateProjectByID;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectContributionService {

    private final ValidateProjectByID validateProjectByID;
    private final AuthenticatedUserProvider userProvider;
    private final ProjectContributionRepository projectContributionRepository;
    private final EnrollmentRepository enrollmentRepository;

    public ProjectContributionService(ValidateProjectByID validateProjectByID, AuthenticatedUserProvider userProvider, ProjectContributionRepository projectContributionRepository, EnrollmentRepository enrollmentRepository) {
        this.validateProjectByID = validateProjectByID;
        this.userProvider = userProvider;
        this.projectContributionRepository = projectContributionRepository;
        this.enrollmentRepository = enrollmentRepository;
    }


    //TODO:estas contribuciones solos la deberan hacer os usuarios que esten enrolados o el creador del proyecto
    @Transactional
    public void addContribution(Long projectId, ProjectContributionCreateDTO dto) {
        Project project = validateProjectByID.validateExistsAndEnabled(projectId);
        User currentUser = userProvider.getCurrentUser();

        // Verifica si el usuario es el creador del proyecto
        boolean isOwner = project.getCreatedBy().getId().equals(currentUser.getId());

        // Verifica si el usuario está enrolado
        boolean isEnrolled = enrollmentRepository.existsByUserAndContentTypeAndContentId(
                currentUser, ContentType.PROJECT, projectId
        );

        if (!isOwner && !isEnrolled) {
            throw new AppException("No tienes permiso para contribuir en este proyecto", ErrorCode.ACCESS_DENIED);
        }

        ProjectContribution contribution = ProjectContributionMapper.toEntity(dto, currentUser, project);

        projectContributionRepository.save(contribution);
    }
}
