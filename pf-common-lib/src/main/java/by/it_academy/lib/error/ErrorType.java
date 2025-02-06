package by.it_academy.lib.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ErrorType {
    @JsonProperty("error")
    ERROR,
    @JsonProperty("structured_error")
    STRUCTURED_ERROR
}
