package alumnithon.skilllink.infrastructure.config; // Keeping it in the 'config' package as per your request


import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; // Import for @Component annotation
import org.springframework.web.filter.OncePerRequestFilter; // Import for OncePerRequestFilter

import java.io.IOException; // Import for IOException and ServletException

/**
 * Custom Security Filter for processing requests before Spring Security's
 * UsernamePasswordAuthenticationFilter. This is typically used for
 * token-based authentication (e.g., JWT).
 */
@Component // Marks this class as a Spring component, allowing it to be autowired/injected
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public SecurityFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null) {
            try {
                authenticateUser(token, request); // Puede lanzar AppException
            } catch ( Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");
                String jsonErrorResponse = "{\"code\": \"UNAUTHORIZED\", \"message\": \"" + ex.getMessage() + "\"}";
                response.getWriter().write(jsonErrorResponse);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token, HttpServletRequest request) {
        try {
            String email = tokenService.getSubject(token);
            if (email == null || email.isEmpty()) {
                throw new AppException("Token inválido: sin subject", ErrorCode.UNAUTHORIZED);
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (userDetails == null) {
                throw new AppException("Usuario no encontrado para el token", ErrorCode.UNAUTHORIZED);
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (UsernameNotFoundException | JWTVerificationException ex) {
            throw new AppException("Token inválido o usuario no encontrado", ErrorCode.UNAUTHORIZED);
        }
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }


}