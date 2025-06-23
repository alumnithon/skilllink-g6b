package alumnithon.skilllink.domain.learning.challenge.service;

import alumnithon.skilllink.domain.auth.service.AuthenticatedUserProvider;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeCreateDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeDetailDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengePreviewDto;
import alumnithon.skilllink.domain.learning.challenge.dto.ChallengeUpdateDto;
import alumnithon.skilllink.domain.learning.challenge.mapper.ChallengeMapper;
import alumnithon.skilllink.domain.learning.challenge.model.Challenge;
import alumnithon.skilllink.domain.learning.challenge.repository.ChallengeRepository;
import alumnithon.skilllink.domain.learning.challenge.validator.IsExistsChallengeByTitle;
import alumnithon.skilllink.domain.learning.challenge.validator.ValidateChallengeByID;
import alumnithon.skilllink.domain.learning.sharedLearning.validator.ValidatorCreatedBy;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final AuthenticatedUserProvider userProvider;
    private final IsExistsChallengeByTitle isExistsChallengeByTitle;
    private final ValidateChallengeByID validateChallengeByID;
    private final ValidatorCreatedBy validatorCreatedBy;

    public ChallengeService(ChallengeRepository challengeRepository, AuthenticatedUserProvider userProvider, IsExistsChallengeByTitle isExistsChallengeByTitle, ValidateChallengeByID validateChallengeByID, ValidatorCreatedBy validatorCreatedBy) {
        this.challengeRepository = challengeRepository;
        this.userProvider = userProvider;
        this.isExistsChallengeByTitle = isExistsChallengeByTitle;
        this.validateChallengeByID = validateChallengeByID;
        this.validatorCreatedBy = validatorCreatedBy;
    }

    //Ver challenger por mentor
    public Page<ChallengePreviewDto> getAllChallengesForMentor(Pageable pageable) {
        return challengeRepository.findByCreatedByIdAndEnabledTrue(userProvider.getCurrentUser().getId(), pageable)
                .map(ChallengeMapper::toPreviewDto);
    }

    public ChallengeDetailDto getChallengeByIdForMentor(Long id) {

        Challenge challenge = challengeRepository.findByIdAndEnabledTrueAndCreatedBy_Id(id, userProvider.getCurrentUser().getId())
                .orElseThrow(() -> new AppException("Recurso no encontrado", ErrorCode.NOT_FOUND));

        return ChallengeMapper.toDetailDto(challenge);
    }

    //Crear Challenger
    public ChallengePreviewDto createChallengeByMentor(@Valid ChallengeCreateDto dto) {
        //validar y asignar el usuario logeado
        var creator = userProvider.getCurrentUser();
        //validar si ya existe challenge con el mismo title para el mismo creador
        isExistsChallengeByTitle.validatorsChallenge(dto,null, creator.getId());

        Challenge challenge = ChallengeMapper.toEntity(dto, creator);
        challengeRepository.save(challenge);
        return ChallengeMapper.toPreviewDto(challenge);
    }

    @Transactional
    public ChallengeDetailDto updateChallengeByMentor(Long id, @Valid ChallengeUpdateDto dto) {
        //valida y asigna el id del usuario logueado
        Long creatorId = userProvider.getCurrentUser().getId();
        //Verifica que exista recurso por el ID indicado
        Challenge challenge = validateChallengeByID.validateExistsAndEnabled(id);
        //Valida si el usuario logueado es creador del recurso
        validatorCreatedBy.validateOwnedByMentor(challenge, creatorId);
        //si pasan el Titulo valida si existe
        if (dto.title() != null && !dto.title().isEmpty()){
            isExistsChallengeByTitle.validatorsChallenge(dto, id, creatorId);
        }
        challenge.updateFromDto(dto);
        return ChallengeMapper.toDetailDto(challenge);
    }

}
