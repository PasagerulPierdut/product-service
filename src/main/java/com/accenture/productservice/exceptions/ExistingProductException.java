package com.accenture.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingProductException extends RuntimeException {

    public ExistingProductException() {
    }

    public ExistingProductException(String message) {
        super.getLocalizedMessage();
    }

    public ExistingProductException(String message, Throwable err) {
        super(message, err);
    }
}

