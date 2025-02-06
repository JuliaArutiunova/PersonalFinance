package by.it_academy.lib.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StructuredErrorResponse {
    private ErrorType logref = ErrorType.STRUCTURED_ERROR;
    @JsonProperty("errors")
    private List<FieldErrorDto> fieldErrorDtos;

    public StructuredErrorResponse(List<FieldErrorDto> fieldErrorDtos) {
        this.fieldErrorDtos = fieldErrorDtos;
    }
}
