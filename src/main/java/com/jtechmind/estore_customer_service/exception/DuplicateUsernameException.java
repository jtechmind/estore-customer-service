package com.jtechmind.estore_customer_service.exception;

import java.util.Map;

public class DuplicateUsernameException extends RuntimeException {
    private Map<String, String> errors;
    public DuplicateUsernameException(String message) {
        super(message);
    }
    public Map<String, String> getErrors() {
        return errors;
    }
}
