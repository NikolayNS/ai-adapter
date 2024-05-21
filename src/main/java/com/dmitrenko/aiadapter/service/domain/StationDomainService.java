package com.dmitrenko.aiadapter.service.domain;

import com.dmitrenko.aiadapter.model.dto.request.StationReq;
import com.dmitrenko.aiadapter.model.dto.view.StationDetailView;
import com.dmitrenko.aiadapter.model.dto.view.StationView;

import java.util.UUID;

public interface StationDomainService {

    StationView add(StationReq req);

    StationView get(UUID id);

    StationDetailView getDetail(UUID id);

    StationDetailView addAccount(UUID id, UUID accountId);

    StationView update(UUID id, StationReq req);

    boolean delete(UUID id);
}
