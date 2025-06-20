package alumnithon.skilllink.domain.userprofile.service;

import alumnithon.skilllink.domain.userprofile.model.Profile;
import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.CountryRepository;
import alumnithon.skilllink.domain.userprofile.repository.ProfileRepository;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import alumnithon.skilllink.domain.userprofile.dto.RegisterProfileDto;

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

    public void Create(RegisterProfileDto registred) {
      
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            Long userId = user.getId();
             if(!userRepository.existsById(userId)){
               throw new IllegalStateException("The user does not exist");
           }
           if(profileRepository.existsByUser(user)){
                throw new IllegalStateException("The profile already exists");
            }

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
            
            if(user.getRole() != Role.ROLE_USER){
                profile.setCertifications(registred.getCertifications());
            }
               profileRepository.save(profile);
           }
        }
    }
