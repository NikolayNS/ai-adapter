package com.dmitrenko.aiadapter.model;

import java.util.Arrays;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;
import lombok.Getter;

@Getter
public enum FunctionEnum {
    TEST_ACTION("testAction"),
    SWITCH_LAMP("switchLamp"),
    SWITCH_SOCKET("switchSocket"),
	SET_AC_TEMPERATURE("setACTemperature");

    private final String value;

    FunctionEnum(String value) {
        this.value = value;
    }

    public static FunctionEnum fromValue(String value) {
        return Arrays.stream(FunctionEnum.values())
                .filter(o -> o.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new EnumValueNotFoundException(String.format("Unexpected MessageRole value %s", value)));
    }
}