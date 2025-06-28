package alumnithon.skilllink.domain.learning.project.dto;

import alumnithon.skilllink.domain.learning.project.model.ContributionType;
import alumnithon.skilllink.domain.shared.interfaces.ValidEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectContributionCreateDTO(
        @NotNull(message = "La descripción no puede ser nula")
        @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
        String description,

        @NotNull(message = "El tipo de contribución es obligatorio")
        @ValidEnum(enumClass = ContributionType.class)
        ContributionType contributionType,

        @Min(value = 1, message = "El progreso debe ser mayor que 0")
        @Max(value = 100, message = "El progreso no puede ser mayor que 100")
        Integer progressContributed
) {
}
