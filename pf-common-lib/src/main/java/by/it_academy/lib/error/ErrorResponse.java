package by.it_academy.lib.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private ErrorType logref = ErrorType.ERROR;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
