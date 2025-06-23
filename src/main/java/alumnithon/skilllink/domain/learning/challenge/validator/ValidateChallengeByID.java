package alumnithon.skilllink.domain.learning.challenge.validator;

import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import alumnithon.skilllink.domain.learning.challenge.repository.ChallengeRepository;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class ValidateChallengeByID {

    private final ChallengeRepository repository;

    public ValidateChallengeByID(ChallengeRepository repository) {
        this.repository = repository;
    }

    public Challenge validateExistsAndEnabled(Long id) {
        System.out.println("Estory aca");
        return repository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new AppException("Desafío no encontrado o deshabilitado", ErrorCode.NOT_FOUND));
    }
}
