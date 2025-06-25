package alumnithon.skilllink.domain.learning.enrollment.dto;

import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;

public record EnrollmentRequestDTO(
        ContentType contentType
) {
}
