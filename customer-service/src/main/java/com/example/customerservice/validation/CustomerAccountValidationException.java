package com.example.customerservice.validation;

public class CustomerAccountValidationException extends RuntimeException {
    public CustomerAccountValidationException(String message) {
        super(message);
    }

}
