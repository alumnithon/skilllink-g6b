package alumnithon.skilllink.domain.learning.project.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectPreviewDTO(
        Long id,
        String title,
        String description,
        String status,
        String difficultyLevel,
        String createdBy,
        LocalDateTime createdAt,
        List<String> tags
) {
}
