package alumnithon.skilllink.domain.auth.service;

import alumnithon.skilllink.domain.auth.dto.AuthUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import alumnithon.skilllink.domain.auth.dto.AuthRequestDTO;
import alumnithon.skilllink.domain.auth.dto.AuthResponseDTO;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.infrastructure.config.TokenService;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

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
        logger.info("Authenticating user with email: {}", authRequestDTO.email());
        if (user != null) {
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    authRequestDTO.email(), authRequestDTO.password());
            var userAuthenticated = authenticationManager.authenticate(authToken);
            logger.info("User successfully authenticated: {}", userAuthenticated.getName());
            var token  = tokenService.generateToken((User) userAuthenticated.getPrincipal());
            String imageUrl = user.getImage_url();
            if (imageUrl != null && imageUrl.startsWith("/")) {
                imageUrl = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(imageUrl)
                        .toUriString();
            }
            return new AuthResponseDTO(
                    new AuthUserDTO(user.getId(), user.getName() , user.getRole().name(), imageUrl),
                    token
            );
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
