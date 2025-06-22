package alumnithon.skilllink.domain.learning.challenge.validator;

import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeUpdateDto;
import alumnithon.skilllink.domain.learning.challenge.repository.ChallengeRepository;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class IsExistsChallengeByTitle implements ValidatorsChallenge {
    private final ChallengeRepository challengeRepository;

    public IsExistsChallengeByTitle(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }
    @Override
    public void validatorsChallenge(Object data, Long id) {
        if (data instanceof ChallengeCreateDto requestDTO) {
            if (challengeRepository.existsByTitleAndEnabledAndCreatedBy(requestDTO.title(), id)) {
                throw new AppException("Ya existe un desafio con ese título", ErrorCode.CONFLICT);
            }
        } else if (data instanceof ChallengeUpdateDto updateDTO) {
            if (challengeRepository.existsByTitleAndEnabledAndCreatedBy(updateDTO.title(), id)
                    && !challengeRepository.findById(id).get().getTitle().equals(updateDTO.title())) {
                throw new AppException("Ya existe un desafio con ese título", ErrorCode.CONFLICT);
            }
        }
    }
}
