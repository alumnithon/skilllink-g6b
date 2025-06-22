package alumnithon.skilllink.domain.learning.challenge.dto;

import alumnithon.skilllink.domain.learning.shared.model.DifficultyLevel;

import java.time.LocalDate;

public record ChallengeUpdateDto(
        String title,
        String description,
        DifficultyLevel difficultyLevel,
        LocalDate deadline
) {
}
