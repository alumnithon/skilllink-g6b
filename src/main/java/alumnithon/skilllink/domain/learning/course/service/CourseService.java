package alumnithon.skilllink.domain.learning.course.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CoursePreviewDTO;
import alumnithon.skilllink.domain.learning.course.mapper.CourseMapper;
import alumnithon.skilllink.domain.learning.course.model.Course;
import alumnithon.skilllink.domain.learning.course.repository.CourseRepository;
import alumnithon.skilllink.domain.learning.course.validator.IsExistsCourseByTitle;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final AuthenticatedUserProvider userProvider;
    private final IsExistsCourseByTitle isExistsCourseByTitle;

    public CourseService(CourseRepository courseRepository, AuthenticatedUserProvider userProvider, IsExistsCourseByTitle isExistsCourseByTitle) {
        this.courseRepository = courseRepository;
        this.userProvider = userProvider;
        this.isExistsCourseByTitle = isExistsCourseByTitle;
    }

    public CoursePreviewDTO createCourseByMentor(@Valid CourseCreateDTO dto) {
        var creator = userProvider.getCurrentUser();
        isExistsCourseByTitle.validatorsCourse(dto,null, creator.getId());
        Course course = CourseMapper.toEntity(dto, creator);
        courseRepository.save(course);
        return CourseMapper.toPreviewDTO(course);
    }

}
