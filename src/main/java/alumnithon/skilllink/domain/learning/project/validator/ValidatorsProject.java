package alumnithon.skilllink.domain.learning.project.validator;

import jakarta.validation.Valid;

public interface ValidatorsProject {
    void validatorsProject(@Valid Object datos, Long projectId, Long creatorId );
}
