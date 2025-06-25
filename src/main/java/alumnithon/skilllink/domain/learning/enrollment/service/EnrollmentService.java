package alumnithon.skilllink.domain.learning.enrollment.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.challenge.service.ChallengeService;
import alumnithon.skilllink.domain.learning.course.service.CourseService;
import alumnithon.skilllink.domain.learning.enrollment.dto.EnrollmentContentDTO;
import alumnithon.skilllink.domain.learning.enrollment.dto.EnrollmentRequestDTO;
import alumnithon.skilllink.domain.learning.enrollment.model.Enrollment;
import alumnithon.skilllink.domain.learning.enrollment.repository.EnrollmentRepository;
import alumnithon.skilllink.domain.learning.project.service.ProjectService;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final AuthenticatedUserProvider userProvider;
    private final ProjectService projectService;
    private final CourseService courseService;
    private final ChallengeService challengeService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, AuthenticatedUserProvider userProvider, ProjectService projectService, CourseService courseService, ChallengeService challengeService) {
        this.enrollmentRepository = enrollmentRepository;
        this.userProvider = userProvider;
        this.projectService = projectService;
        this.courseService = courseService;
        this.challengeService = challengeService;
    }

    public void enrollCurrentUser(Long contentId,  EnrollmentRequestDTO dto) {
        var currentUser = userProvider.getCurrentUser();

        boolean exists = enrollmentRepository.existsByUserAndContentTypeAndContentId(
                currentUser, dto.contentType(), contentId);

        if (exists) {
            throw new AppException("Ya estás inscrito en este contenido.", ErrorCode.CONFLICT);
        }

        switch (dto.contentType()) {
            case PROJECT -> projectService.getProjectPreviewById(contentId);
            case COURSE -> courseService.getCoursePreviewById(contentId);
            case CHALLENGE -> challengeService.getChallengePreviewById(contentId);        }

        Enrollment enrollment = new Enrollment(currentUser, dto.contentType(), contentId);
        enrollmentRepository.save(enrollment);
    }

    public List<EnrollmentContentDTO> getEnrolledContentByUser() {

        var currentUser = userProvider.getCurrentUser();

        List<Enrollment> enrollments = enrollmentRepository.findByUserId(currentUser.getId());

        return enrollments.stream().map(enrollment -> {
            Object contentPreview = switch (enrollment.getContentType()) {
                case PROJECT -> projectService.getProjectPreviewById(enrollment.getContentId());
                case COURSE -> courseService.getCoursePreviewById(enrollment.getContentId());
                case CHALLENGE -> challengeService.getChallengePreviewById(enrollment.getContentId());
            };

            return new EnrollmentContentDTO(
                    enrollment.getContentType(),
                    enrollment.getContentId(),
                    contentPreview
            );
        }).toList();
    }
}
