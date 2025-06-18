package alumnithon.skilllink.shared.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    
    // 4xx: Errores del cliente
    BAD_REQUEST(HttpStatus.BAD_REQUEST),                          // 400
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),                        // 401
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED),                 // 401 (credenciales incorrectas)
    FORBIDDEN(HttpStatus.FORBIDDEN),                              // 403 (sin permisos)
    NOT_FOUND(HttpStatus.NOT_FOUND),                              // 404
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED),            // 405
    CONFLICT(HttpStatus.CONFLICT),                                // 409
    GONE(HttpStatus.GONE),                                        // 410
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE),    // 415
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY),        // 422

    // 5xx: Errores del servidor
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),      // 500
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED),                  // 501
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY),                          // 502
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE),          // 503
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT),                  // 504

    // Personalizados
    TOKEN_EXPIRED(HttpStatus.GONE),
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED),
    RESOURCE_ALREADY_EXISTS(HttpStatus.CONFLICT),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),
    INVALID_INPUT(HttpStatus.BAD_REQUEST),
    RATE_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS),            // 429

    // Seguridad
    ACCESS_DENIED(HttpStatus.FORBIDDEN),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED);

    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
