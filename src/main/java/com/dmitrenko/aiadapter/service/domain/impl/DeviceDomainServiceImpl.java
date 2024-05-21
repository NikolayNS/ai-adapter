package com.dmitrenko.aiadapter.service.domain.impl;

import com.dmitrenko.aiadapter.mapper.view.DeviceMapper;
import com.dmitrenko.aiadapter.model.dto.request.AccountReq;
import com.dmitrenko.aiadapter.model.dto.request.DeviceReq;
import com.dmitrenko.aiadapter.model.dto.view.AccountDetailView;
import com.dmitrenko.aiadapter.model.dto.view.AccountView;
import com.dmitrenko.aiadapter.model.dto.view.DeviceDetailView;
import com.dmitrenko.aiadapter.model.dto.view.DeviceView;
import com.dmitrenko.aiadapter.model.entity.Account;
import com.dmitrenko.aiadapter.model.entity.Device;
import com.dmitrenko.aiadapter.model.entity.DeviceStatus;
import com.dmitrenko.aiadapter.model.entity.action.Action;
import com.dmitrenko.aiadapter.repository.DeviceRepository;
import com.dmitrenko.aiadapter.repository.StationRepository;
import com.dmitrenko.aiadapter.service.domain.DeviceDomainService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceDomainServiceImpl implements DeviceDomainService {

    private final DeviceRepository deviceRepository;
    private final StationRepository stationRepository;

    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public DeviceView add(DeviceReq req) {
        if(deviceRepository.existsByExternalId(req.getExternalId()))
            throw new EntityExistsException(String.format("Device with externalId [%s] already exist", req.getExternalId()));

        Device device = deviceMapper.toEntity(req);
        device = deviceRepository.saveAndFlush(device);

        return deviceMapper.toView(device);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceView get(UUID id) {
        return deviceMapper.toView(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceDetailView getDetail(UUID id) {
        return deviceMapper.toDetailView(findById(id));
    }

    @Override
    @Transactional
    public DeviceView update(UUID id, DeviceReq req) {
        Device device = findById(id);
        device = deviceMapper.merge(device, req);
        device = deviceRepository.saveAndFlush(device);

        return deviceMapper.toView(device);
    }

    @Override
    @Transactional
    public DeviceView setStatus(UUID id, DeviceStatus status) {
        Device device = findById(id);
        device.setStatus(status);
        device = deviceRepository.saveAndFlush(device);

        return deviceMapper.toView(device);
    }

    @Override
    @Transactional
    public DeviceDetailView addStation(UUID id, UUID stationId) {
        var station = stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Station with id [%s] not found", id)));

        Device device = findById(id);
        device.setStation(station);
        device = deviceRepository.saveAndFlush(device);

        return deviceMapper.toDetailView(device);
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        if(!deviceRepository.existsById(id))
            throw new EntityExistsException(String.format("Device with id [%s] not found", id));

        deviceRepository.deleteById(id);

        return !deviceRepository.existsById(id);
    }

    private Device findById(UUID id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Device with id [%s] not found", id)));
    }
}
