package com.dmitrenko.aiadapter.exception;

public class EnumValueNotFoundException extends RuntimeException {

    public EnumValueNotFoundException() {
        super();
    }

    public EnumValueNotFoundException(String message) {
        super(message);
    }

    public EnumValueNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
