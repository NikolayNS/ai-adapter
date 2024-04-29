package com.dmitrenko.aiadapter.exception;

public class FileNotReadable extends RuntimeException {

    public FileNotReadable() {
        super();
    }

    public FileNotReadable(String message) {
        super(message);
    }

    public FileNotReadable(String message, Throwable cause) {
        super(message, cause);
    }
}
