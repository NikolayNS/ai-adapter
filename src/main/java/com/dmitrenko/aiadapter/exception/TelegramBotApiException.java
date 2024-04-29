package com.dmitrenko.aiadapter.exception;

public class TelegramBotApiException extends RuntimeException {

    public TelegramBotApiException() {
        super();
    }

    public TelegramBotApiException(String message) {
        super(message);
    }
}
