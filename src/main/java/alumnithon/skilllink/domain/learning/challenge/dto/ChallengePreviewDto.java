package alumnithon.skilllink.domain.learning.challenge.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;

import java.time.LocalDate;

public record ChallengePreviewDto(
        Long id,
        String title,
        DifficultyLevel difficultyLevel,
        LocalDate deadline
) {
}
