package alumnithon.skilllink.domain.learning.sharedLearning.validator;

import alumnithon.skilllink.domain.learning.sharedLearning.interfaces.OwnableByMentor;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ValidatorCreatedBy {
    public void validateOwnedByMentor(OwnableByMentor entity, Long mentorId) {
        if (!entity.getCreatedBy().getId().equals(mentorId)) {
            throw new AppException("No tienes permiso para modificar este recurso", ErrorCode.ACCESS_DENIED);
        }
    }
}
