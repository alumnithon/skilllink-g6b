package alumnithon.skilllink.domain.learning.course.validator;

import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseUpdateDTO;
import alumnithon.skilllink.domain.learning.course.repository.CourseRepository;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class IsExistsCourseByTitle implements ValidatorsCourse {
    private final CourseRepository courseRepository;

    public IsExistsCourseByTitle(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void validatorsCourse(Object data, Long CourserId, Long createdId) {
        if (data instanceof CourseCreateDTO requestDTO) {
            if (courseRepository.existsByTitleAndEnabledAndCreatedBy(requestDTO.title(), createdId)) {
                throw new AppException("Ya existe un desafio con ese título", ErrorCode.CONFLICT);
            }
        } else if (data instanceof CourseUpdateDTO updateDTO) {
            if (courseRepository.existsByTitleAndEnabledAndCreatedBy(updateDTO.title(), createdId)
                    && !courseRepository.findById(CourserId).get().getTitle().equals(updateDTO.title())) {
                throw new AppException("Ya existe un desafio con ese título", ErrorCode.CONFLICT);
            }
        }
    }
}
