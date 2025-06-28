package alumnithon.skilllink.domain.learning.course.dto;

import java.util.List;

public record CoursePreviewDTO(
        Long id,
        String title,
        String description,
        Boolean hasCertification,
        List<String> tags
) {
}
