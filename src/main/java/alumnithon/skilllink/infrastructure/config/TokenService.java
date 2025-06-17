package alumnithon.skilllink.infrastructure.config;

import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {

    private final String apiSecret;
    private static final String ISSUER = "skilllink";
    private static final int EXPIRATION_DAYS = 30;
    @Value("${app.zone-time:America/Bogota}") // valor por defecto si no se define
    private String zoneTime;

    public TokenService(@Value("${api.security.secret}") String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = getAlgorithm();
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("uid", user.getId().toString())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new AppException("Error al generar el token", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String getSubject(String token) {
        if (token == null || token.isBlank()) {

            throw new AppException("Token nulo o vacío", ErrorCode.UNAUTHORIZED);
        }

        try {
            DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            String subject = decodedJWT.getSubject();
            if (subject == null || subject.isBlank()) {
                throw new AppException("Token inválido: sin subject", ErrorCode.UNAUTHORIZED);
            }

            return subject;
        } catch (JWTVerificationException ex) {

            throw new AppException("Token inválido o expirado", ErrorCode.UNAUTHORIZED );
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(apiSecret);
    }

    private Instant getExpirationDate() {
        ZoneId zoneId = ZoneId.of(zoneTime);
        return LocalDateTime.now(zoneId)
                .plusDays(EXPIRATION_DAYS)
                .toInstant(zoneId.getRules().getOffset(LocalDateTime.now(zoneId)));
    }

}
