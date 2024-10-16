package org.damap.base.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import java.util.Map;

@Provider
public class ConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    // Extract the constraint violations and format them into a response-friendly structure
    List<Map<String, String>> violations =
        exception.getConstraintViolations().stream()
            .map(
                violation ->
                    Map.of(
                        "path", violation.getPropertyPath().toString(),
                        "message", violation.getMessage(),
                        "invalidValue", String.valueOf(violation.getInvalidValue())))
            .toList();

    // Create the response body with the violations
    Map<String, Object> responseBody =
        Map.of("exception", "ConstraintViolationException", "violations", violations);

    // Build and return the response
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(responseBody)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
