package com.dmitrenko.aiadapter.model.dto.openai.request;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;

import java.util.Arrays;

public enum LampActionEnum {
    ON("ON"),
    OFF("OFF"),
    TOGGLE("TOGGLE");

    private final String value;

    LampActionEnum(String value) {
        this.value = value;
    }

    public static LampActionEnum fromValue(String value) {
        return Arrays.stream(LampActionEnum.values())
                .filter(o -> o.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new EnumValueNotFoundException(String.format("Unexpected LampActionEnum value %s", value)));
    }
}
