package alumnithon.skilllink.domain.learning.course.validator;

import alumnithon.skilllink.domain.learning.course.model.Course;
import alumnithon.skilllink.domain.learning.course.repository.CourseRepository;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ValidateCourseByID {
    private final CourseRepository repository;

    public ValidateCourseByID(CourseRepository repository) {
        this.repository = repository;
    }

    public Course validateExistsAndEnabled(Long id) {
        return repository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new AppException("Curso no encontrado o deshabilitado", ErrorCode.NOT_FOUND));
    }
}
