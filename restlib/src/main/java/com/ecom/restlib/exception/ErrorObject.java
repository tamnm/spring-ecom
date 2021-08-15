package com.ecom.restlib.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Collection;

@Data
@Builder
public class ErrorObject {
    private static final String ACCESS_DENIED = "Access denied!";
    private static final String INVALID_REQUEST = "Invalid request";

    private Instant timestamp;
    private int status;
    private Collection<String> errors;
    private String type;
    private String path;
    private String message;

    public static ErrorObject build(final WebRequest request,
                                    final HttpStatus status,
                                    final Collection<String> errors) {
        return ErrorObject.builder()
                          .timestamp(Instant.now())
                          .status(status.value())
                          .message(getMessageForStatus(status))
                          .path(request.getDescription(false))
                          .errors(errors)
                          .build();
    }

    private static String getMessageForStatus(HttpStatus status) {
        switch (status) {
            case UNAUTHORIZED:
                return ACCESS_DENIED;
            case BAD_REQUEST:
                return INVALID_REQUEST;
            default:
                return status.getReasonPhrase();
        }
    }
}
