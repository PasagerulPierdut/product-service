package com.accenture.productservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleException(ProductNotFoundException exception, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Product not found in database.");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingProductException.class)
    public ResponseEntity<Object> handleException(ExistingProductException exception, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Product is already registered in database.");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, String.valueOf(ex.getFieldErrors()), errors);
        return handleExceptionInternal(ex, details, headers, details.getStatus(), request);
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({ConstraintViolationException.class})
    public ExceptionResponse handleException(ConstraintViolationException exception) {
        log.debug("Exception registered: {}", exception.getConstraintViolations(), exception);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return new ExceptionResponse(exception.getClass().getSimpleName(), message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleException(Exception exception) {
        log.debug("Exception registered: {}", exception.getCause(), exception);
        return new ExceptionResponse(exception.getClass().getSimpleName(), "Exception occurred inside API.");
    }
}
