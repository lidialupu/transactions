package com.webservice.exceptions;

public class DuplicateException extends RuntimeException{

    public DuplicateException(String m) {
        super(m+ " This transaction is already in our database");
    }

}
