package alumnithon.skilllink.domain.userprofile.service;

import org.springframework.stereotype.Service;

import alumnithon.skilllink.domain.userprofile.dto.RegisterRequest;
import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.infrastructure.config.SecurityConfig;
import jakarta.validation.Valid;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public RegisterService(SecurityConfig securityConfig, UserRepository userRepository){
       this.securityConfig = securityConfig;
       this.userRepository = userRepository;
    }

    public User createUser(@Valid RegisterRequest user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("The email has already been registered before");
        }
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());

        //This is where encoding is applied
        String hasedPasword = securityConfig.passwordEncoder().encode(user.getPassword());
        newUser.setPassword(hasedPasword);

        newUser.setRole(Role.ROLE_USER);
        newUser.setImage_url(user.getImage_url());
        newUser.setEnabled(true);

        return userRepository.save(newUser);
    }
}
