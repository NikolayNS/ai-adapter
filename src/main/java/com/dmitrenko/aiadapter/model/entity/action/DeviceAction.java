package com.dmitrenko.aiadapter.model.entity.action;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;

import java.util.Arrays;

public enum DeviceAction implements Action {
    ON("ON"),
    OFF("OFF"),
    TOGGLE("TOGGLE");

    private final String value;

    DeviceAction(String value) {
        this.value = value;
    }

    public static DeviceAction fromValue(String value) {
        return Arrays.stream(DeviceAction.values())
                .filter(o -> o.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new EnumValueNotFoundException(String.format("Unexpected DeviceAction value %s", value)));
    }

    @Override
    public String getValue() {
        return value;
    }
}
