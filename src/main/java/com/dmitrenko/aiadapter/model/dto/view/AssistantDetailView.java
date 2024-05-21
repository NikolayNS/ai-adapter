package com.dmitrenko.aiadapter.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class AssistantDetailView {
    private UUID id;
    private String externalId;
    private List<ConversationView> conversations = new LinkedList<>();
    private LocalDateTime createdAt;
}
