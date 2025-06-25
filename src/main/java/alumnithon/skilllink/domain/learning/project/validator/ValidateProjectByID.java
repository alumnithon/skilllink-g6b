package alumnithon.skilllink.domain.learning.project.validator;

import alumnithon.skilllink.domain.learning.project.model.Project;
import alumnithon.skilllink.domain.learning.project.model.ProjectStatus;
import alumnithon.skilllink.domain.learning.project.repository.ProjectRepository;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ValidateProjectByID {
    private final ProjectRepository repository;

    public ValidateProjectByID(ProjectRepository repository) {
        this.repository = repository;
    }


    public Project validateExistsAndEnabled(Long id) {
        return repository.findByIdAndStatusNot(id, ProjectStatus.ARCHIVED)
                .orElseThrow(() -> new AppException("Curso no encontrado o deshabilitado", ErrorCode.NOT_FOUND));
    }
}