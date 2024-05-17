package com.dmitrenko.aiadapter.model.dto.mqtt;

import java.util.Map;

import com.dmitrenko.aiadapter.model.dto.mqtt.enums.EventType;
import lombok.Data;

@Data
public class RawEventDTO {
    private EventType type;
    private Map<String, Object> data;
}