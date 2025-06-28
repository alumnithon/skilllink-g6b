package alumnithon.skilllink.domain.learning.challenge.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;

import java.time.LocalDate;
import java.util.List;

public record ChallengeDetailDto(
        Long id,
        String title,
        String description,
        DifficultyLevel difficultyLevel,
        LocalDate createdAt,
        LocalDate deadline,
        Long createdByUserId,
        String createdByUserName,
        List<String> tags
) {
}
