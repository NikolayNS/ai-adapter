package com.dmitrenko.aiadapter.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ConversationView {
    private UUID id;
    private String conversationThreadId;
    private String dailyContext;
    private Map<LocalDate, String> summary = new HashMap<>();
    private LocalDateTime createdAt;
}
