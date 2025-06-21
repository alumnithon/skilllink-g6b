package alumnithon.skilllink.domain.userprofile.service;

import alumnithon.skilllink.domain.userprofile.dto.UserDto;
import alumnithon.skilllink.domain.userprofile.model.*;
import alumnithon.skilllink.domain.userprofile.repository.CountryRepository;
import alumnithon.skilllink.domain.userprofile.repository.ProfileRepository;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import alumnithon.skilllink.domain.userprofile.dto.CountryDto;
import alumnithon.skilllink.domain.userprofile.dto.GetProfileDto;
import alumnithon.skilllink.domain.userprofile.dto.RegistrerProfileDto;

@Service
public class ProfileService{
    public final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, CountryRepository countryRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }

    public void Create(RegistrerProfileDto registred) {
      
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       // El usuario autenticado no puede ser nulo y tiene que ser de Tipo Usuario
        if(authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            Long userId = user.getId();
             if(!userRepository.existsById(userId)){
               throw new IllegalStateException("The user does not exist");
           }
             //Deve existir en base de datos un usuario con el que se relacionara el Perfil
           if(profileRepository.existsByUser(user)){
                throw new IllegalStateException("The profile already exists");
            }
            //Se coonvierte de Datos mapeados con DTO a una Entidad para Persistir
            Profile profile = new Profile();
            profile.setBio(registred.getBio());
            profile.setOccupation(registred.getOcupation());
            profile.setExperience(registred.getExperience());
            profile.setSkills(registred.getSkills());
            profile.setInterests(registred.getInterests());
            profile.setSocialLinks(registred.getSocialLinks());
            profile.setContactEmail(registred.getContactEmail());
            profile.setContactPhone(registred.getContactPhone());
            profile.setCountry(countryRepository.getById(registred.getCountryId()) );
            profile.setUser(user);

            //Solo Perfiles ROLE_ADMIN y ROLE_MENTOR Pueden guardar Certificaciones
            if(user.getRole() != Role.ROLE_USER){
                profile.setCertifications(registred.getCertifications());
            }
            //Guarda la información en base de datos
               profileRepository.save(profile);
           }
        }

    public GetProfileDto GetProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       //Usuario autenticado no deve ser nulo
        if (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof User)) {
            throw new IllegalStateException("User not authenticated");
        }
        //Obtenemos el usuario y el Id
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        //Comprobamos que el usuario exista en la base de datos
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("The user does not exist");
        }
        // Obtenemos los datos del perfil y si no existe perfil lanza una exepcion
        Profile profile = profileRepository.findByUser(user);
        if (profile == null) {
            throw new IllegalStateException("The profile does not exist");
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
           getProfileDto.setOcupation(profile.getOccupation());
           getProfileDto.setExperience(profile.getExperience());
           getProfileDto.setVisibility(profile.getVisibility().name());
           getProfileDto.setSkills(profile.getSkills());
           getProfileDto.setInterests(profile.getInterests());
           getProfileDto.setSocialLinks(profile.getSocialLinks());
           getProfileDto.setContactEmail(profile.getContactEmail());
           getProfileDto.setContactPhone(profile.getContactPhone());
           getProfileDto.setCountry(countryDto);
           getProfileDto.setCertifications(profile.getCertifications());
           //Retorna toda la informacion
           return getProfileDto;        
    }

    public void Update(RegistrerProfileDto update) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            throw new IllegalStateException("User not authenticated");
        }

        User user = (User) authentication.getPrincipal();
       
        // El usuario tiene que tener un perfil creado para modificarlo
        Profile profile = profileRepository.findByUser(user);
        if (profile == null) {
            throw new IllegalStateException("Profile does not exist. Please create it first.");
        }

        // Evita campos nulos, se puede actualizar parte de los datos del perfil)
        if (update.getBio() != null) profile.setBio(update.getBio());
        if (update.getLocation() != null) profile.setLocation(update.getLocation());
        if (update.getOcupation() != null) profile.setOccupation(update.getOcupation());
        if (update.getExperience() != null) profile.setExperience(update.getExperience());
        if (update.getSkills() != null) profile.setSkills(update.getSkills());
        if (update.getInterests() != null) profile.setInterests(update.getInterests());
        if (update.getSocialLinks() != null) profile.setSocialLinks(update.getSocialLinks());
        if (update.getContactEmail() != null) profile.setContactEmail(update.getContactEmail());
        if (update.getContactPhone() != null) profile.setContactPhone(update.getContactPhone());

        // Actualizar país si viene un ID válido
        if (update.getCountryId() != null) {
            Country country = countryRepository.findById(update.getCountryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid country ID"));
            profile.setCountry(country);
        }

        // Solo si el rol permite editar certificaciones
        if (user.getRole() != Role.ROLE_USER && update.getCertifications() != null) {
            profile.setCertifications(update.getCertifications());
        }

        // Visibilidad
        if ((profile.getVisibility()) != null) {
            profile.setVisibility(ProfileVisibility.valueOf(update.getVisibility()));
        }

        profileRepository.save(profile);
   
    }
    }
