package com.accenture.productservice.exceptions;

public class NoSuchCategoryException extends RuntimeException{

    public NoSuchCategoryException(String message) {
        super(message);
    }
}
