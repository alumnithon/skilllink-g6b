package alumnithon.skilllink.domain.learning.project.validator;

import alumnithon.skilllink.domain.learning.project.dto.ProjectCreateDTO;
import alumnithon.skilllink.domain.learning.project.dto.ProjectUpdateDTO;
import alumnithon.skilllink.domain.learning.project.repository.ProjectRepository;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class IsExistsProjectByTitle implements ValidatorsProject {

    private final ProjectRepository projectRepository;

    public IsExistsProjectByTitle(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void validatorsProject(Object data, Long projectId, Long createdId) {
        if (data instanceof ProjectCreateDTO requestDTO) {
            if (projectRepository.existsByTitleAndCreatedById(requestDTO.title(), createdId)) {
                throw new AppException("Ya existe un Proyecto con ese título", ErrorCode.CONFLICT);
            }
        } else if (data instanceof ProjectUpdateDTO updateDTO) {
            if (projectRepository.existsByTitleAndCreatedById(updateDTO.title(), createdId)
                    && !projectRepository.findById(projectId).get().getTitle().equals(updateDTO.title())) {
                throw new AppException("Ya existe un Proyecto con ese título", ErrorCode.CONFLICT);
            }
        }
    }
}
