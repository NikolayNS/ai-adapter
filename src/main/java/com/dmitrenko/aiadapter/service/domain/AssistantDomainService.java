package com.dmitrenko.aiadapter.service.domain;

import com.dmitrenko.aiadapter.model.dto.request.AssistantReq;
import com.dmitrenko.aiadapter.model.dto.view.AssistantDetailView;
import com.dmitrenko.aiadapter.model.dto.view.AssistantView;

import java.util.UUID;

public interface AssistantDomainService {

    AssistantView add(AssistantReq req);

    AssistantView get(UUID id);

    AssistantDetailView getDetail(UUID id);

    boolean delete(UUID id);
}
