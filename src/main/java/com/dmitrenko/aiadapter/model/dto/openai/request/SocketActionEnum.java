package com.dmitrenko.aiadapter.model.dto.openai.request;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;

import java.util.Arrays;

public enum SocketActionEnum {
    ON("ON"),
    OFF("OFF");

    private final String value;

    SocketActionEnum(String value) {
        this.value = value;
    }

    public static SocketActionEnum fromValue(String value) {
        return Arrays.stream(SocketActionEnum.values())
                .filter(o -> o.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new EnumValueNotFoundException(String.format("Unexpected SocketActionEnum value %s", value)));
    }
}
