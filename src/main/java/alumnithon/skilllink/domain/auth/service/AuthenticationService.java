package alumnithon.skilllink.domain.auth.service;

import alumnithon.skilllink.domain.auth.dto.AuthRequestDTO;
import alumnithon.skilllink.domain.auth.dto.AuthResponseDTO;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.infrastructure.config.TokenService;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public AuthResponseDTO authenticateUser(AuthRequestDTO authRequestDTO){
        User user = userRepository.findByEmail(authRequestDTO.email());
        System.out.println(user.getEmail());
        if (user != null) {
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    authRequestDTO.email(), authRequestDTO.password());
            var userAuthenticated = authenticationManager.authenticate(authToken);
            System.out.println(userAuthenticated);
            var token  = tokenService.generateToken((User) userAuthenticated.getPrincipal());
            return new AuthResponseDTO(token, user.getRole().name(), user.getName());
        } else {
            throw new AppException("Las credenciales proporcionadas son incorrectas.", ErrorCode.UNAUTHORIZED );
        }
    }


    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new AppException("El usuario autenticado no es válido.", ErrorCode.FORBIDDEN);
    }
}
