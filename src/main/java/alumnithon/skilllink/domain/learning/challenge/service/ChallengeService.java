package alumnithon.skilllink.domain.learning.challenge.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.mapper.ChallengeMapper;
import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import alumnithon.skilllink.domain.learning.challenge.repository.ChallengeRepository;
import alumnithon.skilllink.domain.learning.challenge.validator.IsExistsChallengeByTitle;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final AuthenticatedUserProvider userProvider;
    private final IsExistsChallengeByTitle isExistsChallengeByTitle;

    public ChallengeService(ChallengeRepository challengeRepository, AuthenticatedUserProvider userProvider, IsExistsChallengeByTitle isExistsChallengeByTitle) {
        this.challengeRepository = challengeRepository;
        this.userProvider = userProvider;
        this.isExistsChallengeByTitle = isExistsChallengeByTitle;
    }

    public ChallengePreviewDto createChallengeByMentor(@Valid ChallengeCreateDto dto) {
        //validar y asignar el usuario logeado
        var creator = userProvider.getCurrentUser();
        //validar si ya existe challenge con el mismo title para el mismo creador
        isExistsChallengeByTitle.validatorsChallenge(dto, creator.getId());

        Challenge challenge = ChallengeMapper.toEntity(dto, creator);
        challengeRepository.save(challenge);
        return ChallengeMapper.toPreviewDto(challenge);
    }

}
