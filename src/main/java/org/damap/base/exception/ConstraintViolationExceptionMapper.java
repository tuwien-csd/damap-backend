package org.damap.base.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class ConstraintViolationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Extract the constraint violations and format them into a response-friendly structure
        List<ValidationError> violations =
                exception.getConstraintViolations().stream()
                        .map(
                                violation -> {
                                    ValidationError validationError = new ValidationError();
                                    validationError.setPath(violation.getPropertyPath().toString());
                                    validationError.setMessage(violation.getMessage());
                                    validationError.setInvalidValue(String.valueOf(violation.getInvalidValue()));

                                    return validationError;
                                })
                        .toList();

        // Create the response body with the violations
        ErrorResponse responseBody = new ErrorResponse();
        responseBody.setHttpStatus(Response.Status.BAD_REQUEST.getStatusCode());
        responseBody.setMessage("Constraint violation");
        responseBody.setValidationErrors(violations);
        responseBody.setWarnings(List.of());

        // Build and return the response
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(responseBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}