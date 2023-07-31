package com.zaychik.learning.system_user_rest.controller.exception;

public class NotFoundException  extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
