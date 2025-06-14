package alumnithon.skilllink.infrastructure.config; // Keeping it in the 'config' package as per your request

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    // You will likely need to inject services here for your authentication logic.
    // For example, if you're using JWTs, you might inject a JwtService and a UserDetailsService:
    // private final JwtService jwtService;
    // private final UserDetailsService userDetailsService;

    // Example constructor for dependency injection:
    // public SecurityFilter(JwtService jwtService, UserDetailsService userDetailsService) {
    //     this.jwtService = jwtService;
    //     this.userDetailsService = userDetailsService;
    // }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // --- Your Custom Authentication Logic Goes Here ---

        // 1. Extract authentication details (e.g., JWT token from Authorization header)
        // String token = request.getHeader("Authorization");
        // if (token != null && token.startsWith("Bearer ")) {
        //     token = token.substring(7); // Remove "Bearer " prefix
        //     try {
        //         // 2. Validate the token and parse claims
        //         // String username = jwtService.extractUsername(token);
        //
        //         // 3. Load user details
        //         // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //
        //         // 4. Create an Authentication object and set it in the SecurityContextHolder
        //         // UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        //         //     userDetails, null, userDetails.getAuthorities());
        //         // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //         // SecurityContextHolder.getContext().setAuthentication(authentication);
        //
        //     } catch (Exception e) {
        //         // Handle token validation errors (e.g., expired, invalid signature)
        //         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        //         return; // Stop the filter chain here if authentication fails
        //     }
        // }

        System.out.println("Executing custom SecurityFilter for path: " + request.getRequestURI());

        // --- End of Custom Authentication Logic ---

        // Crucial: Continue the filter chain. If you don't call this, the request
        // will not proceed to the next filters or your controllers.
        filterChain.doFilter(request, response);
    }
}