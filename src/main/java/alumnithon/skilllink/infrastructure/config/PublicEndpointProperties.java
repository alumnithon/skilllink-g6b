package alumnithon.skilllink.infrastructure.config;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class PublicEndpointProperties {
    public List<PublicEndpoint> getEndpoints() {
        return List.of(
                new PublicEndpoint("/api/auth/*", HttpMethod.POST),
                new PublicEndpoint("/api/auth/ping", HttpMethod.GET),
                new PublicEndpoint("/api/docs", HttpMethod.GET),
                new PublicEndpoint("/api/docs/swagger-config", HttpMethod.GET),
                new PublicEndpoint("/api/docs/swagger-ui/**", HttpMethod.GET),
                new PublicEndpoint("/api/v3/api-docs/**", HttpMethod.GET)
        );
    }
}
