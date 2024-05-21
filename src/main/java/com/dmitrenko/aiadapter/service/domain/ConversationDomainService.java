package com.dmitrenko.aiadapter.service.domain;

import com.dmitrenko.aiadapter.model.dto.view.ConversationDetailView;
import com.dmitrenko.aiadapter.model.dto.view.ConversationView;

import java.util.UUID;

public interface ConversationDomainService {

    ConversationView add(String conversationThreadId);

    ConversationView get(UUID id);

    ConversationDetailView getDetail(UUID id);

    ConversationDetailView addProfile(UUID id, UUID profileId);

    ConversationDetailView addAssistant(UUID id, UUID assistantId);

    boolean updateContext(UUID id, String context);

    boolean updateSummary(UUID id, String summary);

    boolean delete(UUID id);
}
