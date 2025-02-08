package com.jtechmind.estore_customer_service.exception;

import java.util.Map;

public class DuplicateEmailException extends RuntimeException{
    private Map<String, String> errors;
    public DuplicateEmailException(String message) {
        super(message);
    }
    public Map<String, String> getErrors() {
        return errors;
    }

}
