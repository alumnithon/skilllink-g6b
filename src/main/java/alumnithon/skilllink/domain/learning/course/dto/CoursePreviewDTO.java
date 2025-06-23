package alumnithon.skilllink.domain.learning.course.dto;

public record CoursePreviewDTO(
        Long id,
        String title,
        String description,
        Boolean hasCertification
) {
}
