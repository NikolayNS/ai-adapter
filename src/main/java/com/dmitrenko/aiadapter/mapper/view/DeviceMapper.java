package com.dmitrenko.aiadapter.mapper.view;

import com.dmitrenko.aiadapter.model.dto.request.DeviceReq;
import com.dmitrenko.aiadapter.model.dto.view.DeviceDetailView;
import com.dmitrenko.aiadapter.model.dto.view.DeviceView;
import com.dmitrenko.aiadapter.model.entity.Device;
import com.dmitrenko.aiadapter.model.entity.DeviceStatus;
import com.dmitrenko.aiadapter.model.entity.action.DeviceAction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceMapper {

    private final StationMapper stationMapper;

    public DeviceMapper(@Lazy StationMapper stationMapper) {
        this.stationMapper = stationMapper;
    }

    public DeviceView toView(Device source) {
        return new DeviceView()
                .setId(source.getId())
                .setExternalId(source.getExternalId())
                .setName(source.getName())
                .setDescription(source.getDescription())
                .setActions(Arrays.stream(source.getActions()).collect(Collectors.toSet()))
                .setStatus(source.getStatus())
                .setCreatedAt(source.getCreatedAt());
    }

    public DeviceDetailView toDetailView(Device source) {
        return new DeviceDetailView()
                .setId(source.getId())
                .setExternalId(source.getExternalId())
                .setName(source.getName())
                .setDescription(source.getDescription())
                .setActions(Arrays.stream(source.getActions()).collect(Collectors.toSet()))
                .setStatus(source.getStatus())
                .setStation(source.getStation() == null ? null : stationMapper.toView(source.getStation()))
                .setCreatedAt(source.getCreatedAt());
    }

    public Device toEntity(DeviceReq source) {
        return new Device()
                .setExternalId(source.getExternalId())
                .setName(source.getName())
                .setDescription(source.getDescription())
                .setActions(source.getActions().toArray(DeviceAction[]::new))
                .setStatus(DeviceStatus.NEW);
    }

    public Device merge(Device target, DeviceReq source) {
        var common = Arrays.stream(target.getActions()).collect(Collectors.toCollection(HashSet::new));
        common.addAll(source.getActions());
        return target
                .setExternalId(source.getExternalId() == null ? target.getExternalId() : source.getExternalId())
                .setName(source.getName() == null ? target.getName() : source.getName())
                .setDescription(source.getDescription() == null ? target.getDescription() : source.getDescription())
                .setActions(common.toArray(DeviceAction[]::new));
    }

    public List<DeviceView> toView(Collection<Device> source) {
        return source.stream().map(this::toView).toList();
    }

    public List<DeviceDetailView> toDetailView(Collection<Device> source) {
        return source.stream().map(this::toDetailView).toList();
    }

    public List<Device> toEntity(Collection<DeviceReq> source) {
        return source.stream().map(this::toEntity).toList();
    }
}
