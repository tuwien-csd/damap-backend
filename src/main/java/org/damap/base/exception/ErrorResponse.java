package org.damap.base.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    Integer httpStatus;
    String message;

    List<ValidationError> validationErrors;
    List<String> warnings;

}
