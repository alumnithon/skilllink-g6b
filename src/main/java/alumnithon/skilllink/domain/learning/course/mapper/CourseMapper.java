package alumnithon.skilllink.domain.learning.course.mapper;

import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CoursePreviewDTO;
import alumnithon.skilllink.domain.learning.course.model.Course;
import alumnithon.skilllink.domain.userprofile.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CourseMapper {
    public static Course toEntity(CourseCreateDTO dto, User creator) {
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

    public static CoursePreviewDTO toPreviewDTO(Course course) {
        return new CoursePreviewDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getHasCertification()
        );
    }
}
