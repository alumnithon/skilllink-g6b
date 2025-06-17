package alumnithon.skilllink.shared.exception;

import alumnithon.skilllink.shared.exception.dto.ErrorResponse;
import alumnithon.skilllink.shared.exception.dto.ValidationError;
import alumnithon.skilllink.shared.exception.dto.ValidationErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        return buildErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return buildErrorResponse(ErrorCode.INVALID_CREDENTIALS, "Las credenciales proporcionadas son incorrectas.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .findFirst()
                .map(violation -> buildErrorResponse(ErrorCode.BAD_REQUEST, violation.getMessage()))
                .orElse(buildErrorResponse(ErrorCode.BAD_REQUEST, "Violación de restricciones de validación."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                ErrorCode.VALIDATION_ERROR.name(),
                "Error de validación en uno o más campos.",
                errors
        );

        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getHttpStatus()).body(response);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Formato de la solicitud inválido. Verifique los datos enviados.";
        Throwable rootCause = ex.getMostSpecificCause();

        return buildErrorResponse(ErrorCode.BAD_REQUEST, message);
    }



    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode.name(), message));
    }
    /** Metodo que obtiene el orden de los campos de un DTO */
    private List<String> getFieldOrder(Class<?> dtoClass) {
        return Arrays.stream(dtoClass.getDeclaredFields())
                .map(Field::getName)
                .toList();
    }

}
