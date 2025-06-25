package alumnithon.skilllink.domain.learning.challenge.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;

import java.time.LocalDate;
import java.util.List;

public record ChallengePreviewDto(
        Long id,
        String title,
        DifficultyLevel difficultyLevel,
        LocalDate deadline,
        List<String> tags
) {
}
