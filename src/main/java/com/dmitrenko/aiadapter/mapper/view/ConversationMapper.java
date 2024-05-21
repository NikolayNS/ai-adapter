package com.dmitrenko.aiadapter.mapper.view;

import com.dmitrenko.aiadapter.model.dto.view.ConversationDetailView;
import com.dmitrenko.aiadapter.model.dto.view.ConversationView;
import com.dmitrenko.aiadapter.model.entity.Conversation;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ConversationMapper {

    private final ProfileMapper profileMapper;
    private final AssistantMapper assistantMapper;

    public ConversationMapper(@Lazy ProfileMapper profileMapper, @Lazy AssistantMapper assistantMapper) {
        this.profileMapper = profileMapper;
        this.assistantMapper = assistantMapper;
    }

    public ConversationView toView(Conversation source) {
        return new ConversationView()
                .setId(source.getId())
                .setConversationThreadId(source.getConversationThreadId())
                .setDailyContext(source.getDailyContext())
                .setSummary(source.getSummary())
                .setCreatedAt(source.getCreatedAt());
    }

    public ConversationDetailView toDetailView(Conversation source) {
        return new ConversationDetailView()
                .setId(source.getId())
                .setConversationThreadId(source.getConversationThreadId())
                .setDailyContext(source.getDailyContext())
                .setSummary(source.getSummary())
                .setProfile(source.getProfile() == null ? null : profileMapper.toView(source.getProfile()))
                .setAssistant(source.getAssistant() == null ? null : assistantMapper.toView(source.getAssistant()))
                .setCreatedAt(source.getCreatedAt());
    }

    public List<ConversationView> toView(Collection<Conversation> source) {
        return source.stream().map(this::toView).toList();
    }

    public List<ConversationDetailView> toDetailView(Collection<Conversation> source) {
        return source.stream().map(this::toDetailView).toList();
    }
}
