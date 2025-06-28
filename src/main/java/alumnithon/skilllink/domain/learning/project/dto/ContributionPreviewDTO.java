package alumnithon.skilllink.domain.learning.project.dto;

import java.time.LocalDateTime;

public record ContributionPreviewDTO(
        Long id,
        String username,
        String description,
        String contributionType,
        Integer progressContributed,
        LocalDateTime createdAt
) {
}
