package com.dmitrenko.aiadapter.model.dto.mqtt;

import com.dmitrenko.aiadapter.model.dto.mqtt.enums.EventType;
import lombok.Data;

@Data
public class InterviewEventDTO {
    private EventType type;
    private InterviewEventDetailsDTO data;
}