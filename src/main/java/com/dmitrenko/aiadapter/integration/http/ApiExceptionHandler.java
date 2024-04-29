package com.dmitrenko.aiadapter.integration.http;

import com.dmitrenko.aiadapter.exception.ClientResponseException;
import com.dmitrenko.aiadapter.exception.ServerResponseException;
import com.dmitrenko.aiadapter.exception.TelegramBotApiException;
import com.dmitrenko.aiadapter.model.dto.ErrorResponse;
import jakarta.validation.ValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ErrorResponse handle(ValidationException e) {
        return new ErrorResponse()
            .setCode(String.valueOf(SERVICE_UNAVAILABLE.value()))
            .setMessage(e.getMessage());
    }

    @ExceptionHandler({ClientResponseException.class})
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ErrorResponse handle(ClientResponseException e) {
        return new ErrorResponse()
            .setCode(String.valueOf(SERVICE_UNAVAILABLE.value()))
            .setMessage(e.getBodyErrorResponse());
    }

    @ExceptionHandler({ServerResponseException.class})
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ErrorResponse handle(ServerResponseException e) {
        return new ErrorResponse()
            .setCode(String.valueOf(SERVICE_UNAVAILABLE.value()))
            .setMessage(e.getBodyErrorResponse());
    }

    @ExceptionHandler({TelegramBotApiException.class})
    @ResponseStatus(SERVICE_UNAVAILABLE)
    public ErrorResponse handle(TelegramBotApiException e) {
        return new ErrorResponse()
                .setCode(String.valueOf(SERVICE_UNAVAILABLE.value()))
                .setMessage(e.getMessage());
    }
}
