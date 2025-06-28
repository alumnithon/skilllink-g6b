package alumnithon.skilllink.domain.learning.project.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;
import alumnithon.skilllink.domain.shared.interfaces.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProjectCreateDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255, message = "El título no debe superar los 255 caracteres")
        String title,

        @NotBlank(message = "La descripción es obligatoria")
        String description,

        @NotNull(message = "El nivel de dificultad es obligatorio")
        @ValidEnum(enumClass = DifficultyLevel.class)
        DifficultyLevel difficultyLevel,

        @NotNull(message = "Debe proporcionar al menos un tag")
        @Size(min = 1, message = "Debe proporcionar al menos un tag")
        List<String> tagsName //Lista de tags para asociar
) {
}
