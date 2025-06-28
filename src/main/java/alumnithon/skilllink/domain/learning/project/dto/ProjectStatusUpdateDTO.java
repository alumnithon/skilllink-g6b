package alumnithon.skilllink.domain.learning.project.dto;

import alumnithon.skilllink.domain.learning.project.model.ProjectStatus;
import alumnithon.skilllink.domain.shared.interfaces.ValidEnum;
import jakarta.validation.constraints.NotNull;

public record ProjectStatusUpdateDTO(
        @NotNull(message = "El estado no puede ser nulo")
        @ValidEnum(enumClass = ProjectStatus.class)
        ProjectStatus status
) {
}
