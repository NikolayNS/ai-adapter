package com.dmitrenko.aiadapter.service.domain.impl;

import com.dmitrenko.aiadapter.mapper.view.AssistantMapper;
import com.dmitrenko.aiadapter.model.dto.request.AssistantReq;
import com.dmitrenko.aiadapter.model.dto.view.AssistantDetailView;
import com.dmitrenko.aiadapter.model.dto.view.AssistantView;
import com.dmitrenko.aiadapter.model.entity.Assistant;
import com.dmitrenko.aiadapter.repository.AssistantRepository;
import com.dmitrenko.aiadapter.service.domain.AssistantDomainService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssistantDomainServiceImpl implements AssistantDomainService {

    private final AssistantRepository assistantRepository;

    private final AssistantMapper assistantMapper;

    @Override
    @Transactional
    public AssistantView add(AssistantReq req) {
        if(assistantRepository.existsByExternalId(req.getExternalId()))
            throw new EntityExistsException(String.format("Assistant with externalId [%s] already exist", req.getExternalId()));

        Assistant assistant = assistantMapper.toEntity(req);
        assistant = assistantRepository.saveAndFlush(assistant);

        return assistantMapper.toView(assistant);
    }

    @Override
    @Transactional(readOnly = true)
    public AssistantView get(UUID id) {
        return assistantMapper.toView(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public AssistantDetailView getDetail(UUID id) {
        return assistantMapper.toDetailView(findById(id));
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        if(!assistantRepository.existsById(id))
            throw new EntityExistsException(String.format("Assistant with id [%s] not found", id));

        assistantRepository.deleteById(id);

        return !assistantRepository.existsById(id);
    }

    private Assistant findById(UUID id) {
        return assistantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Assistant with id [%s] not found", id)));
    }
}
