package alumnithon.skilllink.domain.auth.service;

import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {
    private final UserRepository userRepository;

    public AuthenticatedUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AppException("Usuario no autenticado", ErrorCode.FORBIDDEN);
        }

        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        User userAuthenticated = userRepository.findByEmailAndEnabledTrue(username);
        if (userAuthenticated == null) {
            throw new AppException("Usuario no autenticado", ErrorCode.FORBIDDEN);
        }

        return userAuthenticated;

    }
}
