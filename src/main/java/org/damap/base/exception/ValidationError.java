package org.damap.base.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationError {
    private String path;
    private String message;
    private String invalidValue;
}
