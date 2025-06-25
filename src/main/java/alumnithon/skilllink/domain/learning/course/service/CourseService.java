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
import alumnithon.skilllink.domain.learning.sharedLearning.dto.TagsToContentDTO;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.learning.sharedLearning.service.ContentTagService;
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
    private final ContentTagService contentTagService;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, AuthenticatedUserProvider userProvider, IsExistsCourseByTitle isExistsCourseByTitle, ValidateCourseByID validateCourseByID, ValidatorCreatedBy validatorCreatedBy, ContentTagService contentTagService, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.userProvider = userProvider;
        this.isExistsCourseByTitle = isExistsCourseByTitle;
        this.validateCourseByID = validateCourseByID;
        this.validatorCreatedBy = validatorCreatedBy;
        this.contentTagService = contentTagService;
        this.courseMapper = courseMapper;
    }

    public Page<CoursePreviewDTO> getAllEnabledCoursesByMentor(Pageable pageable) {
        return courseRepository.findByCreatedByIdAndEnabledTrue(userProvider.getCurrentUser().getId(), pageable)
                .map(courseMapper::toPreviewDTO);
    }

    public CourseDetailDTO getEnabledCourseByIdForMentor(Long id) {

        Course course = courseRepository.findByIdAndEnabledTrueAndCreatedBy_Id(id, userProvider.getCurrentUser().getId())
                .orElseThrow(() -> new AppException("Recurso no encontrado", ErrorCode.NOT_FOUND));

        return courseMapper.toDetailDTO(course);
    }

    public CoursePreviewDTO createCourseByMentor(@Valid CourseCreateDTO dto) {
        var creator = userProvider.getCurrentUser();
        isExistsCourseByTitle.validatorsCourse(dto,null, creator.getId());
        Course course = courseMapper.toEntity(dto, creator);
        courseRepository.save(course);

        // Agrega tags relacionados
        if (dto.tagsName() != null && !dto.tagsName().isEmpty()) {
            TagsToContentDTO tagsDTO = new TagsToContentDTO(
                    ContentType.COURSE,
                    course.getId(),
                    dto.tagsName()
            );
            contentTagService.addTagsToContent(tagsDTO);
        }
        return courseMapper.toPreviewDTO(course);
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
        return courseMapper.toDetailDTO(course);
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
                .map(courseMapper::toPreviewDTO)
                .toList();
    }

    public CourseDetailDTO getEnabledCourseById(Long id) {
        Course course = courseRepository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new AppException("Course not found or disabled", ErrorCode.NOT_FOUND));

        return courseMapper.toDetailDTO(course);
    }

    public CoursePreviewDTO getCoursePreviewById(Long id) {
        var course = courseRepository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new AppException("Course not found or disabled", ErrorCode.NOT_FOUND));
        return courseMapper.toPreviewDTO(course);
    }

}
