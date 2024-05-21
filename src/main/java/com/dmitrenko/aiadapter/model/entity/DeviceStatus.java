package com.dmitrenko.aiadapter.model.entity;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DeviceStatus {
    NEW("NEW"),
    ENABLED("ENABLED"),
    DISABLED("DISABLED"),
    REMOVED("REMOVED");

    private final String value;

    DeviceStatus(String value) {
        this.value = value;
    }

    public static DeviceStatus fromValue(String value) {
        return Arrays.stream(DeviceStatus.values())
                .filter(o -> o.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new EnumValueNotFoundException(String.format("Unexpected DeviceStatus value %s", value)));
    }
}
