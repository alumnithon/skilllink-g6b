package alumnithon.skilllink.domain.learning.enrollment.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;

public record EnrollmentContentDTO(
        ContentType contentType,
        Long contentId,
        Object contentPreview
) {
}
