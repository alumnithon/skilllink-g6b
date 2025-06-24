package alumnithon.skilllink.domain.learning.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseUpdateDTO(
        @NotBlank(message = "Title is required")
        @Size(max = 100)
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 1000)
        String description,

        @NotNull(message = "Certification flag is required")
        Boolean hasCertification
) {
}
