package alumnithon.skilllink.domain.userprofile.service;

import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import alumnithon.skilllink.domain.userprofile.dto.RegisterRequestDto;
import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.infrastructure.config.SecurityConfig;
import jakarta.validation.Valid;

import java.util.Arrays;

@Service
public class RegisterService {

    private static final String DEFAULT_AVATAR_URL = "/images/default-avatar.png";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder){
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
    }

    public User createUser(@Valid RegisterRequestDto user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new AppException("The email has already been registered before", ErrorCode.CONFLICT);
        }
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());

        //This is where encoding is applied
        String hasedPasword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(hasedPasword);

        newUser.setRole(Role.valueOf(user.getRole()));
        String imageUrl = user.getImage_url();
        newUser.setImage_url((imageUrl == null || imageUrl.trim().isEmpty()) ? DEFAULT_AVATAR_URL : imageUrl.trim());

        newUser.setEnabled(true);
        return userRepository.save(newUser);
    }
}
