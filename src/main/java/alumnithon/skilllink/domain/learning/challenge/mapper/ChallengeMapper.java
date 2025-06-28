package alumnithon.skilllink.domain.learning.challenge.mapper;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeDetailDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.learning.sharedLearning.service.ContentTagService;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ChallengeMapper {

    private final ContentTagService contentTagService;

    public ChallengeMapper(ContentTagService contentTagService) {
        this.contentTagService = contentTagService;
    }
    // Crear Challenge a partir del DTO
    public Challenge toEntity(ChallengeCreateDto dto, User creator) {
        return new Challenge(
                dto.title(),
                dto.description(),
                dto.difficultyLevel(),
                dto.deadline(),
                creator
        );
    }

    // Convertir a DTO de vista previa
    public ChallengePreviewDto toPreviewDto(Challenge challenge) {

        List<String> tagNames = contentTagService.getTagNamesByContent(ContentType.CHALLENGE, challenge.getId());

        return new ChallengePreviewDto(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getDifficultyLevel(),
                challenge.getDeadline(),
                tagNames
        );
    }

    // Convertir a DTO detallado
    public ChallengeDetailDto toDetailDto(Challenge challenge) {
        List<String> tagNames = contentTagService.getTagNamesByContent(ContentType.CHALLENGE, challenge.getId());

        return new ChallengeDetailDto(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getDescription(),
                challenge.getDifficultyLevel(),
                challenge.getCreatedAt().toLocalDate(),
                challenge.getDeadline(),
                challenge.getCreatedBy().getId(),
                challenge.getCreatedBy().getName(),
                tagNames
        );
    }
}
