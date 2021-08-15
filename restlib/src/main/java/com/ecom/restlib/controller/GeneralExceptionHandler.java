package com.ecom.restlib.controller;

import com.ecom.restlib.exception.DataNotFoundException;
import com.ecom.restlib.exception.ErrorObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_MESSAGE_TEMPLATE = "message: %s %n requested uri: %s";
    public static final String LIST_JOIN_DELIMITER = ",";
    public static final String FIELD_ERROR_SEPARATOR = ": ";
    private static final Logger local_logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);
    private static final String ERRORS_FOR_PATH = "errors {} for path {}";

    private static String fiendErrorMessage(FieldError error) {
        return error.getField() + FIELD_ERROR_SEPARATOR +
               error.getDefaultMessage();
    }

    private static String getViolationErrorMessage(ConstraintViolation<?> violation) {
        return violation.getPropertyPath() + FIELD_ERROR_SEPARATOR +
               violation.getMessage();
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                           @NotNull HttpHeaders headers,
                                                                           @NotNull HttpStatus status,
                                                                           @NotNull WebRequest request) {
        List<String> validationErrors = exception.getBindingResult()
                                                 .getFieldErrors()
                                                 .stream()
                                                 .map(GeneralExceptionHandler::fiendErrorMessage)
                                                 .collect(Collectors.toList());
        return getExceptionResponseEntity(exception, HttpStatus.BAD_REQUEST, request, validationErrors);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException exception,
                                                                           @NotNull HttpHeaders headers,
                                                                           @NotNull HttpStatus status,
                                                                           @NotNull WebRequest request) {
        return getExceptionResponseEntity(exception, status, request,
                                          Collections.singletonList(exception.getLocalizedMessage()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException exception,
                                                            WebRequest request) {
        final List<String> validationErrors = exception.getConstraintViolations()
                                                       .stream()
                                                       .map(GeneralExceptionHandler::getViolationErrorMessage)
                                                       .collect(Collectors.toList());
        return getExceptionResponseEntity(exception, HttpStatus.BAD_REQUEST, request, validationErrors);
    }


    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<Object> handleDataNotFound(DataNotFoundException exception, WebRequest request) {
        var errors = Collections.singletonList(exception.getLocalizedMessage());
        return new ResponseEntity<>(ErrorObject.build(request, HttpStatus.NOT_FOUND, errors), HttpStatus.NOT_FOUND);
    }

    /**
     * A general handler for all uncaught exceptions
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        final HttpStatus status = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        final String localizedMessage = exception.getLocalizedMessage();
        final String path = request.getDescription(false);

        String message = (localizedMessage == null || localizedMessage.isEmpty() || localizedMessage.isBlank())
                         ? status.getReasonPhrase()
                         : localizedMessage;

        logger.error(String.format(ERROR_MESSAGE_TEMPLATE, message, path), exception);
        return getExceptionResponseEntity(exception, status, request, Collections.singletonList(message));
    }

    /**
     * Build detailed information about the exception in the response
     */
    private ResponseEntity<Object> getExceptionResponseEntity(final Exception exception,
                                                              final HttpStatus status,
                                                              final WebRequest request,
                                                              final List<String> errors) {
        var errorObject = ErrorObject.build(request, status, errors);
        errorObject.setType(exception.getClass().getSimpleName());

        String errorsMessage;

        if (errors.isEmpty()) {
            errorsMessage = status.getReasonPhrase();
        } else {
            errorsMessage = errors.stream()
                                  .filter(err -> err == null || err.isEmpty() || err.isBlank())
                                  .collect(Collectors.joining(LIST_JOIN_DELIMITER));
        }
        ResponseEntity<Object> response = new ResponseEntity<>(errorObject, status);

        local_logger.error(ERRORS_FOR_PATH, errorsMessage, errorObject.getPath());
        return response;
    }
}