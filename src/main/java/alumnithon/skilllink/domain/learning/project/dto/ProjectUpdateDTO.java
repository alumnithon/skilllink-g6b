package alumnithon.skilllink.domain.learning.project.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.DifficultyLevel;

public record ProjectUpdateDTO(
        String title,
        String description,
        DifficultyLevel difficultyLevel
) {
}
