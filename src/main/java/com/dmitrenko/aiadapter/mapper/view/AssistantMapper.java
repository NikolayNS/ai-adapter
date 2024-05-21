package com.dmitrenko.aiadapter.mapper.view;

import com.dmitrenko.aiadapter.model.dto.request.AssistantReq;
import com.dmitrenko.aiadapter.model.dto.view.AssistantDetailView;
import com.dmitrenko.aiadapter.model.dto.view.AssistantView;
import com.dmitrenko.aiadapter.model.entity.Assistant;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AssistantMapper {

    private final ConversationMapper conversationMapper;

    public AssistantMapper(@Lazy ConversationMapper conversationMapper) {
        this.conversationMapper = conversationMapper;
    }

    public AssistantView toView(Assistant source) {
        return new AssistantView()
                .setId(source.getId())
                .setExternalId(source.getExternalId())
                .setCreatedAt(source.getCreatedAt());
    }

    public AssistantDetailView toDetailView(Assistant source) {
        return new AssistantDetailView()
                .setId(source.getId())
                .setExternalId(source.getExternalId())
                .setConversations(conversationMapper.toView(source.getConversations()))
                .setCreatedAt(source.getCreatedAt());
    }

    public Assistant toEntity(AssistantReq source) {
        return new Assistant()
                .setExternalId(source.getExternalId());
    }

    public List<AssistantView> toView(Collection<Assistant> source) {
        return source.stream().map(this::toView).toList();
    }

    public List<AssistantDetailView> toDetailView(Collection<Assistant> source) {
        return source.stream().map(this::toDetailView).toList();
    }
}
