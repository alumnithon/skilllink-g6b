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
import alumnithon.skilllink.domain.learning.sharedLearning.dto.TagsToContentDTO;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.learning.sharedLearning.service.ContentTagService;
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
    private final ContentTagService contentTagService;
    private final ChallengeMapper challengeMapper;

    public ChallengeService(ChallengeRepository challengeRepository, AuthenticatedUserProvider userProvider, IsExistsChallengeByTitle isExistsChallengeByTitle, ValidateChallengeByID validateChallengeByID, ValidatorCreatedBy validatorCreatedBy, ContentTagService contentTagService, ChallengeMapper challengeMapper) {
        this.challengeRepository = challengeRepository;
        this.userProvider = userProvider;
        this.isExistsChallengeByTitle = isExistsChallengeByTitle;
        this.validateChallengeByID = validateChallengeByID;
        this.validatorCreatedBy = validatorCreatedBy;
        this.contentTagService = contentTagService;
        this.challengeMapper = challengeMapper;
    }

    //Ver challenger por mentor
    public Page<ChallengePreviewDto> getAllChallengesForMentor(Pageable pageable) {
        return challengeRepository.findByCreatedByIdAndEnabledTrue(userProvider.getCurrentUser().getId(), pageable)
                .map(challengeMapper::toPreviewDto);
    }

    public ChallengeDetailDto getChallengeByIdForMentor(Long id) {
        Challenge challenge = challengeRepository.findByIdAndEnabledTrueAndCreatedBy_Id(id, userProvider.getCurrentUser().getId())
                .orElseThrow(() -> new AppException("Recurso no encontrado", ErrorCode.NOT_FOUND));
        return challengeMapper.toDetailDto(challenge);
    }

    //Crear Challenger
    public ChallengePreviewDto createChallengeByMentor(@Valid ChallengeCreateDto dto) {
        //validar y asignar el usuario logeado
        var creator = userProvider.getCurrentUser();
        //validar si ya existe challenge con el mismo title para el mismo creador
        isExistsChallengeByTitle.validatorsChallenge(dto,null, creator.getId());
        Challenge challenge = challengeMapper.toEntity(dto, creator);
        challengeRepository.save(challenge);

        // Agrega tags relacionados
        if (dto.tagsName() != null && !dto.tagsName().isEmpty()) {
            TagsToContentDTO tagsDTO = new TagsToContentDTO(
                    ContentType.CHALLENGE,
                    challenge.getId(),
                    dto.tagsName()
            );
            contentTagService.addTagsToContent(tagsDTO);
        }

        return challengeMapper.toPreviewDto(challenge);
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
        return challengeMapper.toDetailDto(challenge);
    }

    @Transactional
    public void deleteChallengeByMentor(Long id) {
        Challenge challenge = validateChallengeByID.validateExistsAndEnabled(id);
        validatorCreatedBy.validateOwnedByMentor(challenge, userProvider.getCurrentUser().getId());
        challenge.disable();
    }

    //<---- Rutas para todos los los usuarios autenticados  ---->

    public ChallengeDetailDto getChallengeById(Long id) {
        Challenge challenge = validateChallengeByID.validateExistsAndEnabled(id);
        return challengeMapper.toDetailDto(challenge);
    }

    public Page<ChallengePreviewDto> getAllChallenges(Pageable pageable) {
        return challengeRepository.findAllByEnabledTrue(pageable)
                .map(challengeMapper::toPreviewDto);
    }

    public ChallengePreviewDto getChallengePreviewById(Long id) {
        var challenge = challengeRepository.findByIdAndEnabledTrue(id)
                .orElseThrow(() -> new AppException("Challenge not found or disabled", ErrorCode.NOT_FOUND));
        return challengeMapper.toPreviewDto(challenge);
    }

}
