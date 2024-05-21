package com.dmitrenko.aiadapter.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class AssistantView {
    private UUID id;
    private String externalId;
    private LocalDateTime createdAt;
}
