package com.dmitrenko.aiadapter.service.domain.impl;

import com.dmitrenko.aiadapter.mapper.view.ConversationMapper;
import com.dmitrenko.aiadapter.model.dto.view.ConversationDetailView;
import com.dmitrenko.aiadapter.model.dto.view.ConversationView;
import com.dmitrenko.aiadapter.model.entity.*;
import com.dmitrenko.aiadapter.repository.AssistantRepository;
import com.dmitrenko.aiadapter.repository.ConversationRepository;
import com.dmitrenko.aiadapter.repository.ProfileRepository;
import com.dmitrenko.aiadapter.service.domain.ConversationDomainService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationDomainServiceImpl implements ConversationDomainService {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final AssistantRepository assistantRepository;

    private final ConversationMapper conversationMapper;

    @Override
    @Transactional
    public ConversationView add(String conversationThreadId) {
        if(conversationRepository.existsByConversationThreadId(conversationThreadId))
            throw new EntityExistsException(String.format("Conversation with conversationThreadId [%s] already exist", conversationThreadId));

        Conversation conversation = new Conversation().setConversationThreadId(conversationThreadId);
        conversation = conversationRepository.saveAndFlush(conversation);

        return conversationMapper.toView(conversation);
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationView get(UUID id) {
        return conversationMapper.toView(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ConversationDetailView getDetail(UUID id) {
        return conversationMapper.toDetailView(findById(id));
    }

    @Override
    @Transactional
    public ConversationDetailView addProfile(UUID id, UUID profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Profile with id [%s] not found", id)));

        Conversation conversation = findById(id);
        conversation.setProfile(profile);
        conversation = conversationRepository.saveAndFlush(conversation);

        return conversationMapper.toDetailView(conversation);
    }

    @Override
    @Transactional
    public ConversationDetailView addAssistant(UUID id, UUID assistantId) {
        Assistant assistant = assistantRepository.findById(assistantId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Assistant with id [%s] not found", id)));

        Conversation conversation = findById(id);
        conversation.setAssistant(assistant);
        conversation = conversationRepository.saveAndFlush(conversation);

        return conversationMapper.toDetailView(conversation);
    }

    @Override
    @Transactional
    public boolean updateContext(UUID id, String context) {
        return false;
    }

    @Override
    @Transactional
    public boolean updateSummary(UUID id, String summary) {
        return false;
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        if(!conversationRepository.existsById(id))
            throw new EntityExistsException(String.format("Conversation with id [%s] not found", id));

        conversationRepository.deleteById(id);

        return !conversationRepository.existsById(id);
    }

    private Conversation findById(UUID id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Conversation with id [%s] not found", id)));
    }
}
