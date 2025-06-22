package alumnithon.skilllink.domain.learning.challenge.mapper;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeDetailDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChallengeMapper {
    // Crear Challenge a partir del DTO
    public static Challenge toEntity(ChallengeCreateDto dto, User creator) {
        return new Challenge(
                dto.title(),
                dto.description(),
                dto.difficultyLevel(),
                dto.deadline(),
                creator
        );
    }

    // Convertir a DTO de vista previa
    public static ChallengePreviewDto toPreviewDto(Challenge challenge) {
        return new ChallengePreviewDto(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getDifficultyLevel(),
                challenge.getDeadline()
        );
    }

    // Convertir a DTO detallado
    public static ChallengeDetailDto toDetailDto(Challenge challenge) {
        return new ChallengeDetailDto(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getDescription(),
                challenge.getDifficultyLevel(),
                challenge.getCreatedAt().toLocalDate(),
                challenge.getDeadline(),
                challenge.getCreatedBy().getId(),
                challenge.getCreatedBy().getName()
        );
    }
}
