package alumnithon.skilllink.domain.learning.course.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CourseDetailDTO(
        Long id,
        String title,
        String description,
        Boolean hasCertification,
        String createdBy,
        LocalDateTime createdAt,
        List<String> tags
) {
}
