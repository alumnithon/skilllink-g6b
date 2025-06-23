package alumnithon.skilllink.domain.learning.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseCreateDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 100, message = "El título no debe superar los 100 caracteres")
        String title,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 1000, message = "El título no debe superar los 1000 caracteres")
        String description,

        @NotNull(message = "Se requiere la bandera de certificación")
        Boolean hasCertification
) {
}
