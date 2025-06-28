package alumnithon.skilllink.domain.userprofile.service;

import alumnithon.skilllink.domain.userprofile.dto.UserDto;
import alumnithon.skilllink.domain.userprofile.dto.UserPrivateDto;
import alumnithon.skilllink.domain.userprofile.model.*;
import alumnithon.skilllink.domain.userprofile.repository.CountryRepository;
import alumnithon.skilllink.domain.userprofile.repository.ProfileRepository;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alumnithon.skilllink.domain.userprofile.dto.CertificationDto;
import alumnithon.skilllink.domain.userprofile.dto.CountryDto;
import alumnithon.skilllink.domain.userprofile.dto.CountryPrivateDto;
import alumnithon.skilllink.domain.userprofile.dto.GetProfileDto;
import alumnithon.skilllink.domain.userprofile.dto.GetProfilePrivateDTO;
import alumnithon.skilllink.domain.userprofile.dto.RegisterProfileDto;
import alumnithon.skilllink.domain.userprofile.dto.UpdateProfileDto;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileService{
    public final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, CountryRepository countryRepository, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.passwordEncoder = passwordEncoder;    
    }

    public void Create(RegisterProfileDto registred) {
      
            User user = getAuthenticatedUser();
            Long userId = user.getId();
             if(!userRepository.existsById(userId)){
                 throw new AppException("Usuario no se encuentra en base de datos", ErrorCode.NOT_FOUND);
           }
             //Deve existir en base de datos un usuario y no tener un perfil creado
             profileRepository.findByUser(user).ifPresent(profile -> {
                 throw new AppException("Perfil ya existe", ErrorCode.CONFLICT);
             });
            
          //Se coonvierte de Datos mapeados con DTO a una Entidad para Persistir
         
          Profile profile = new Profile();
            profile.setBio(registred.getBio());
            profile.setOccupation(registred.getOccupation());
            profile.setExperience(registred.getExperience());
            profile.setSkills(registred.getSkills());
            profile.setInterests(registred.getInterests());
            profile.setSocialLinks(registred.getSocialLinks());
            profile.setContactEmail(registred.getContactEmail());
            profile.setContactPhone(registred.getContactPhone());

            profile.setCountry(countryRepository.findById(registred.getCountryId()).orElseThrow(() -> new AppException("País no encontrado", ErrorCode.INVALID_INPUT)));
            profile.setUser(user);

            //Solo Perfiles ROLE_ADMIN y ROLE_MENTOR Pueden guardar Certificaciones
            if (user.getRole() != Role.ROLE_USER && registred.getCertifications() != null) {
                List<Certification> certs = registred.getCertifications().stream()
                        .map(dto -> {
                            Certification cert = new Certification();
                            cert.setTitle(dto.getTitle());
                            cert.setUrl(dto.getUrl());
                            return cert;
                        })
                        .collect(Collectors.toList());
                profile.setCertifications(certs);
            }
            //Guarda la información en base de datos
               profileRepository.saveAndFlush(profile);
           }
        
    public GetProfileDto GetProfile() {
       
        //Obtenemos el usuario y el Id
        User user = getAuthenticatedUser();
        Long userId = user.getId();

        //Comprobamos que el usuario exista en la base de datos
        if (!userRepository.existsById(userId)) {
            throw new AppException("El usuario no existe en base de datos", ErrorCode.INVALID_INPUT);
        }
        // Obtenemos los datos del perfil y si no existe perfil lanza una exepcion
        Profile profile = profileRepository.findByUser(user)
        .orElseThrow(()-> new AppException(("Perfil no encontrado"),ErrorCode.INVALID_INPUT));
        
        if (profile == null) {
            throw new AppException("No tienes un perfil aun", ErrorCode.INVALID_INPUT);
        }
        //Pasamos los datos que queremos enviar del Usuario hacia los Dto
        Country country = profile.getCountry();

        UserDto userDto = new UserDto();
           userDto.setName(user.getName());
           userDto.setEmail(user.getEmail());
           userDto.setRole(user.getRole().name());
           userDto.setImage_url(user.getImage_url());
        
        CountryDto countryDto = new CountryDto();
          countryDto.setNombre(country.getNombre());
          countryDto.setName(country.getName());
          countryDto.setIso2(country.getIso2());
          countryDto.setIso3(country.getIso3());
          countryDto.setPhoneCode(country.getPhoneCode());

        GetProfileDto getProfileDto = new GetProfileDto();
           getProfileDto.setUser(userDto); 
           getProfileDto.setBio(profile.getBio());
           getProfileDto.setLocation(profile.getLocation());
           getProfileDto.setOccupation(profile.getOccupation());
           getProfileDto.setExperience(profile.getExperience());
           getProfileDto.setVisibility(profile.getVisibility().name());
           getProfileDto.setSkills(profile.getSkills());
           getProfileDto.setInterests(profile.getInterests());
           getProfileDto.setSocialLinks(profile.getSocialLinks());
           getProfileDto.setContactEmail(profile.getContactEmail());
           getProfileDto.setContactPhone(profile.getContactPhone());
           getProfileDto.setCountry(countryDto);
            List<CertificationDto> certDtos = profile.getCertifications().stream()
        .map(cert -> {
            CertificationDto dto = new CertificationDto();
            dto.setTitle(cert.getTitle());
            dto.setUrl(cert.getUrl());
            return dto;
        })
        .collect(Collectors.toList());

    getProfileDto.setCertifications(certDtos);
           //Retorna toda la informacion
           return getProfileDto;        
    }

    public void Update(UpdateProfileDto update) {

        User user = getAuthenticatedUser();;
       
        // El usuario tiene que tener un perfil creado para modificarlo
        Profile profile = profileRepository.findByUser(user)
        .orElseThrow(() -> new AppException("Perfil no encontrado", ErrorCode.INVALID_INPUT));
       

        // Evita campos nulos, se puede actualizar parte de los datos del perfil)
        if (update.getBio() != null) profile.setBio(update.getBio());
        if (update.getLocation() != null) profile.setLocation(update.getLocation());
        if (update.getOccupation() != null) profile.setOccupation(update.getOccupation());
        if (update.getExperience() != null) profile.setExperience(update.getExperience());
        if (update.getSkills() != null) profile.setSkills(update.getSkills());
        if (update.getInterests() != null) profile.setInterests(update.getInterests());
        if (update.getSocialLinks() != null) profile.setSocialLinks(update.getSocialLinks());
        if (update.getContactEmail() != null) profile.setContactEmail(update.getContactEmail());
        if (update.getContactPhone() != null) profile.setContactPhone(update.getContactPhone());
        if (update.getImageUrl() != null) user.setImage_url(update.getImageUrl());
        if (update.getName() != null)
            user.setName(update.getName());
        if (update.getPassword() != null)
            user.setPassword(passwordEncoder.encode(update.getPassword()));
        
        // Actualizar país si viene un ID válido
        if (update.getCountryId() != null) {
            Country country = getValidatedCountry(update.getCountryId());
            profile.setCountry(country);
        }

        // Solo si el rol permite editar certificaciones
        if (user.getRole() != Role.ROLE_USER && update.getCertifications() != null) {
        List<Certification> certs = update.getCertifications().stream()
            .map(dto -> {
                Certification cert = new Certification();
                cert.setTitle(dto.getTitle());
                cert.setUrl(dto.getUrl());
                return cert;
            })
            .collect(Collectors.toList());
        profile.setCertifications(certs);
    }

        // Visibilidad
        if ((profile.getVisibility()) != null) {
            profile.setVisibility(ProfileVisibility.valueOf(update.getVisibility()));
        }

        profileRepository.save(profile);
   
    }

    public void Delete() {

        User user = getAuthenticatedUser();;
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new AppException("Perfil no encontrado", ErrorCode.FORBIDDEN));

        if (profile == null) {
            throw new AppException("El perfil no existe.", ErrorCode.FORBIDDEN);
        }

        // Elimina el perfil de la base de datos
        profileRepository.delete(profile);
    }

    public Object GetProfileById(Long id) {
        // Verifica si el usuario autenticado es nulo o no es de tipo User.
        //Si esta autenticado retorna el usuario
       getAuthenticatedUser();
        // Verifica si el usuario solicitado existe y está habilitado
       User user = userRepository.findByIdAndEnabledTrue(id);
        if (user == null) {
            throw new AppException("El usuario no existe.", ErrorCode.NOT_FOUND);
        }

        //Verifica que tipo de visibilidad tiene el perfil
        Profile profile = profileRepository.findById(id) 
        .orElseThrow(() ->  new AppException("El perfil no existe.", ErrorCode.NOT_FOUND));

        //Obtiene los datos del Idioma desde el perfil
        Country country = profile.getCountry();

        //Si la visibilidad es privada, muestra verción acotada del Id que solicito.
        if (profile.getVisibility() == ProfileVisibility.PRIVATE) {

            UserPrivateDto userPrivateDto = new UserPrivateDto();
            userPrivateDto.setName(user.getName());
            userPrivateDto.setImage_url(user.getImage_url());

            CountryPrivateDto countryPrivate = new CountryPrivateDto();
            countryPrivate.setName(country.getName());

            GetProfilePrivateDTO getProfilePrivateDto = new GetProfilePrivateDTO();

            getProfilePrivateDto.setUser(userPrivateDto);
            getProfilePrivateDto.setBio(profile.getBio());
            getProfilePrivateDto.setSkills(profile.getSkills());
            getProfilePrivateDto.setInterest(profile.getInterests());
            getProfilePrivateDto.setCountry(countryPrivate.getName());

            return getProfilePrivateDto;

        } else if (profile.getVisibility() == ProfileVisibility.PUBLIC) {
            // Si es público, muestra el perfil completo del Id que solicito.

            UserDto userDto = new UserDto();
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole().name());
            userDto.setImage_url(user.getImage_url());

            CountryDto countryDto = new CountryDto();
            countryDto.setNombre(country.getNombre());
            countryDto.setName(country.getName());
            countryDto.setIso2(country.getIso2());
            countryDto.setIso3(country.getIso3());
            countryDto.setPhoneCode(country.getPhoneCode());

            GetProfileDto getProfileDto = new GetProfileDto();
            getProfileDto.setUser(userDto);
            getProfileDto.setBio(profile.getBio());
            getProfileDto.setLocation(profile.getLocation());
            getProfileDto.setOccupation(profile.getOccupation());
            getProfileDto.setExperience(profile.getExperience());
            getProfileDto.setVisibility(profile.getVisibility().name());
            getProfileDto.setSkills(profile.getSkills());
            getProfileDto.setInterests(profile.getInterests());
            getProfileDto.setSocialLinks(profile.getSocialLinks());
            getProfileDto.setContactEmail(profile.getContactEmail());
            getProfileDto.setContactPhone(profile.getContactPhone());
            getProfileDto.setCountry(countryDto);
            List<CertificationDto> certDtos = profile.getCertifications().stream()
                    .map(cert -> {
                        CertificationDto dto = new CertificationDto();
                        dto.setTitle(cert.getTitle());
                        dto.setUrl(cert.getUrl());
                        return dto;
                    })
                    .collect(Collectors.toList());

            getProfileDto.setCertifications(certDtos);
            //Retorna toda la informacion
            return getProfileDto;
        }
        return null;
    }
    
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new AppException("El Usuario no existe.", ErrorCode.UNAUTHORIZED);
        }
        return (User) authentication.getPrincipal();
    }
    
    private Country getValidatedCountry(Integer countryId) {
        if (countryId == null) {
            return null;
        }
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new AppException(
                        "País no encontrado con ID: " + countryId,
                        ErrorCode.NOT_FOUND));
    }
    }
