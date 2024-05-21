package com.dmitrenko.aiadapter.service.domain;

import com.dmitrenko.aiadapter.model.dto.request.DeviceReq;
import com.dmitrenko.aiadapter.model.dto.view.DeviceDetailView;
import com.dmitrenko.aiadapter.model.dto.view.DeviceView;
import com.dmitrenko.aiadapter.model.entity.DeviceStatus;

import java.util.UUID;

public interface DeviceDomainService {

    DeviceView add(DeviceReq req);

    DeviceView get(UUID id);

    DeviceDetailView getDetail(UUID id);

    DeviceView update(UUID id, DeviceReq req);

    DeviceView setStatus(UUID id, DeviceStatus status);

    DeviceDetailView addStation(UUID id, UUID stationId);

    boolean delete(UUID id);
}
