package alumnithon.skilllink.domain.learning.challenge.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.mapper.ChallengeMapper;
import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import alumnithon.skilllink.domain.learning.challenge.repository.ChallengeRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final AuthenticatedUserProvider userProvider;

    public ChallengeService(ChallengeRepository challengeRepository, AuthenticatedUserProvider userProvider) {
        this.challengeRepository = challengeRepository;
        this.userProvider = userProvider;
    }

    public ChallengePreviewDto createChallengeByMentor(@Valid ChallengeCreateDto dto) {
        Challenge challenge = ChallengeMapper.toEntity(dto, userProvider.getCurrentUser());
        challengeRepository.save(challenge);
        return ChallengeMapper.toPreviewDto(challenge);
    }

}
