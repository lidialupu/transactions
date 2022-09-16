package com.webservice.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message + ": For a transaction to be validated, no fields can be null and the transaction type must be deposit or withdrawal!");
    }
}
