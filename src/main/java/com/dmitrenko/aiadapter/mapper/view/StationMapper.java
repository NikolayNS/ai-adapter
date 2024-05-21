package com.dmitrenko.aiadapter.mapper.view;

import com.dmitrenko.aiadapter.model.dto.request.StationReq;
import com.dmitrenko.aiadapter.model.dto.view.StationDetailView;
import com.dmitrenko.aiadapter.model.dto.view.StationView;
import com.dmitrenko.aiadapter.model.entity.Station;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class StationMapper {

    private final AccountMapper accountMapper;
    private final DeviceMapper deviceMapper;

    public StationMapper(@Lazy AccountMapper accountMapper, @Lazy DeviceMapper deviceMapper) {
        this.accountMapper = accountMapper;
        this.deviceMapper = deviceMapper;
    }

    public StationView toView(Station source) {
        return new StationView()
                .setId(source.getId())
                .setExternalId(source.getExternalId())
                .setName(source.getName())
                .setLocation(source.getLocation());
    }

    public StationDetailView toDetailView(Station source) {
        return new StationDetailView()
                .setId(source.getId())
                .setExternalId(source.getExternalId())
                .setName(source.getName())
                .setLocation(source.getLocation())
                .setAccount(accountMapper.toView(source.getAccount()))
                .setDevices(deviceMapper.toView(source.getDevices()));
    }

    public Station toEntity(StationReq source) {
        return new Station()
                .setExternalId(source.getExternalId())
                .setName(source.getName())
                .setLocation(source.getLocation());
    }

    public Station merge(Station target, StationReq source) {
        return target
                .setExternalId(source.getExternalId() == null ? target.getExternalId() : source.getExternalId())
                .setName(source.getName() == null ? target.getName() : source.getName())
                .setLocation(source.getLocation() == null ? target.getLocation() : source.getLocation());
    }

    public List<StationView> toView(Collection<Station> source) {
        return source.stream().map(this::toView).toList();
    }

    public List<StationDetailView> toDetailView(Collection<Station> source) {
        return source.stream().map(this::toDetailView).toList();
    }

    public List<Station> toEntity(Collection<StationReq> source) {
        return source.stream().map(this::toEntity).toList();
    }
}
