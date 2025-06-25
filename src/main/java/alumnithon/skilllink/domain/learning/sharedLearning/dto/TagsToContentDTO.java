package alumnithon.skilllink.domain.learning.sharedLearning.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;

import java.util.List;

public record TagsToContentDTO(
        ContentType contentType,
        Long contentId,
        List<String> tagsName
) {
}
