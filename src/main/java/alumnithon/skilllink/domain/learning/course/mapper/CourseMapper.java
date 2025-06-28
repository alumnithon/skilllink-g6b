package alumnithon.skilllink.domain.learning.course.mapper;

import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseDetailDTO;
import alumnithon.skilllink.domain.learning.course.dto.CoursePreviewDTO;
import alumnithon.skilllink.domain.learning.course.model.Course;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.learning.sharedLearning.service.ContentTagService;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CourseMapper {

    private final ContentTagService contentTagService;

    public CourseMapper(ContentTagService contentTagService) {
        this.contentTagService = contentTagService;
    }

    public Course toEntity(CourseCreateDTO dto, User creator) {
        return new Course(
                dto.title(),
                dto.description(),
                dto.hasCertification(),
                creator
        );
//        return Course.builder()
//                .title(dto.title())
//                .description(dto.description())
//                .hasCertification(dto.hasCertification())
//                .createdBy(creator)
//                .createdAt(LocalDateTime.now())
//                .enabled(true)
//                .build();
    }

    public CoursePreviewDTO toPreviewDTO(Course course) {
        List<String> tagNames = contentTagService.getTagNamesByContent(ContentType.COURSE, course.getId());

        return new CoursePreviewDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getHasCertification(),
                tagNames
        );
    }

    public CourseDetailDTO toDetailDTO(Course course) {
        List<String> tagNames = contentTagService.getTagNamesByContent(ContentType.COURSE, course.getId());

        return new CourseDetailDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getHasCertification(),
                course.getCreatedBy().getName(),
                course.getCreatedAt(),
                tagNames
        );
    }
}
