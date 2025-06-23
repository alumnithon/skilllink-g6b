package alumnithon.skilllink.domain.learning.course.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.course.dto.CourseCreateDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseDetailDTO;
import alumnithon.skilllink.domain.learning.course.dto.CoursePreviewDTO;
import alumnithon.skilllink.domain.learning.course.dto.CourseUpdateDTO;
import alumnithon.skilllink.domain.learning.course.mapper.CourseMapper;
import alumnithon.skilllink.domain.learning.course.model.Course;
import alumnithon.skilllink.domain.learning.course.repository.CourseRepository;
import alumnithon.skilllink.domain.learning.course.validator.IsExistsCourseByTitle;
import alumnithon.skilllink.domain.learning.course.validator.ValidateCourseByID;
import alumnithon.skilllink.domain.learning.sharedLearning.validator.ValidatorCreatedBy;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final AuthenticatedUserProvider userProvider;
    private final IsExistsCourseByTitle isExistsCourseByTitle;
    private final ValidateCourseByID validateCourseByID;
    private final ValidatorCreatedBy validatorCreatedBy;

    public CourseService(CourseRepository courseRepository, AuthenticatedUserProvider userProvider, IsExistsCourseByTitle isExistsCourseByTitle, ValidateCourseByID validateCourseByID, ValidatorCreatedBy validatorCreatedBy) {
        this.courseRepository = courseRepository;
        this.userProvider = userProvider;
        this.isExistsCourseByTitle = isExistsCourseByTitle;
        this.validateCourseByID = validateCourseByID;
        this.validatorCreatedBy = validatorCreatedBy;
    }

    public Page<CoursePreviewDTO> getAllEnabledCoursesByMentor(Pageable pageable) {
        return courseRepository.findByCreatedByIdAndEnabledTrue(userProvider.getCurrentUser().getId(), pageable)
                .map(CourseMapper::toPreviewDTO);
    }

    public CourseDetailDTO getEnabledCourseByIdForMentor(Long id) {

        Course course = courseRepository.findByIdAndEnabledTrueAndCreatedBy_Id(id, userProvider.getCurrentUser().getId())
                .orElseThrow(() -> new AppException("Recurso no encontrado", ErrorCode.NOT_FOUND));

        return CourseMapper.toDetailDTO(course);
    }

    public CoursePreviewDTO createCourseByMentor(@Valid CourseCreateDTO dto) {
        var creator = userProvider.getCurrentUser();
        isExistsCourseByTitle.validatorsCourse(dto,null, creator.getId());
        Course course = CourseMapper.toEntity(dto, creator);
        courseRepository.save(course);
        return CourseMapper.toPreviewDTO(course);
    }

    @Transactional
    public CourseDetailDTO updateCourseByMentor(Long id, @Valid CourseUpdateDTO dto) {
        var creatorId = userProvider.getCurrentUser().getId();
        Course course = validateCourseByID.validateExistsAndEnabled(id);
        validatorCreatedBy.validateOwnedByMentor(course, creatorId);

        if (dto.title() != null && !dto.title().isEmpty()){
            isExistsCourseByTitle.validatorsCourse(dto, id, creatorId);
        }

        course.update(dto);
        return CourseMapper.toDetailDTO(course);
    }

    @Transactional
    public void deleteCourseByMentor(Long id) {
        Course course = validateCourseByID.validateExistsAndEnabled(id);
        validatorCreatedBy.validateOwnedByMentor(course, userProvider.getCurrentUser().getId());
        course.disable();
    }

    //<---- Servicios publicos para ver los cursos de usuarios autenticados ---->
    public List<CoursePreviewDTO> getAllEnabledCourses() {
        return courseRepository.findByEnabledTrue().stream()
                .map(CourseMapper::toPreviewDTO)
                .toList();
    }

    public CourseDetailDTO getEnabledCourseById(Long id) {
        Course course = courseRepository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new AppException("Course not found or disabled", ErrorCode.NOT_FOUND));

        return CourseMapper.toDetailDTO(course);
    }

}
