package alumnithon.skilllink.shared.exception.dto;

import java.util.List;

public class ValidationErrorResponse {
    private String code;
    private String message;
    private List<ValidationError> errors;

    public ValidationErrorResponse(String code, String message, List<ValidationError> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }


}
