package alumnithon.skilllink.domain.learning.challenge.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;
import alumnithon.skilllink.domain.shared.interfaces.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record ChallengeCreateDto(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255, message = "El título no debe superar los 255 caracteres")
        String title,

        @NotBlank
        String description,

        @NotNull(message = "El nivel de dificultad es obligatorio")
        @ValidEnum(enumClass = DifficultyLevel.class)
        DifficultyLevel difficultyLevel,

        @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate deadline,

        @NotNull(message = "Debe proporcionar al menos un tag")
        @Size(min = 1, message = "Debe proporcionar al menos un tag")
        List<String> tagsName //Lista de tags para asociar
) {
}
