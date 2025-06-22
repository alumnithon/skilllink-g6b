package alumnithon.skilllink.domain.learning.challenge.validator;

import jakarta.validation.Valid;

public interface ValidatorsChallenge {
    void validatorsChallenge(@Valid Object datos, Long id);
}
