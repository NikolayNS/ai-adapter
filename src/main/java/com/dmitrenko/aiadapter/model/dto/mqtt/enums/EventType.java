package com.dmitrenko.aiadapter.model.dto.mqtt.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {
    DEVICE_JOINED("device_joined"),
    DEVICE_INTERVIEW("device_interview"),
    DEVICE_ANNOUNCE("device_announce"),
	DEVICE_LEAVE("device_leave");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}