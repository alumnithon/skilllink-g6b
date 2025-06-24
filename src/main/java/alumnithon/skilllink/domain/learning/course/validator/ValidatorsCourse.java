package alumnithon.skilllink.domain.learning.course.validator;

import jakarta.validation.Valid;

public interface ValidatorsCourse {
    void validatorsCourse(@Valid Object datos, Long challengerId, Long creatorId );
}
