package com.dmitrenko.aiadapter.service.domain.impl;

import com.dmitrenko.aiadapter.mapper.view.StationMapper;
import com.dmitrenko.aiadapter.model.dto.request.StationReq;
import com.dmitrenko.aiadapter.model.dto.view.StationDetailView;
import com.dmitrenko.aiadapter.model.dto.view.StationView;
import com.dmitrenko.aiadapter.model.entity.Account;
import com.dmitrenko.aiadapter.model.entity.Station;
import com.dmitrenko.aiadapter.repository.AccountRepository;
import com.dmitrenko.aiadapter.repository.StationRepository;
import com.dmitrenko.aiadapter.service.domain.StationDomainService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationDomainServiceImpl implements StationDomainService {

    private final StationRepository stationRepository;
    private final AccountRepository accountRepository;

    private final StationMapper stationMapper;

    @Override
    @Transactional
    public StationView add(StationReq req) {
        if(stationRepository.existsByExternalId(req.getExternalId()))
            throw new EntityExistsException(String.format("Station with externalId [%s] already exist", req.getExternalId()));

        Station station = stationMapper.toEntity(req);
        station = stationRepository.saveAndFlush(station);

        return stationMapper.toView(station);
    }

    @Override
    @Transactional(readOnly = true)
    public StationView get(UUID id) {
        return stationMapper.toView(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public StationDetailView getDetail(UUID id) {
        return stationMapper.toDetailView(findById(id));
    }

    @Override
    @Transactional
    public StationDetailView addAccount(UUID id, UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with id [%s] not found", id)));

        Station station = findById(id);
        station.setAccount(account);
        station = stationRepository.saveAndFlush(station);

        return stationMapper.toDetailView(station);
    }

    @Override
    @Transactional
    public StationView update(UUID id, StationReq req) {
        Station station = findById(id);
        station = stationMapper.merge(station, req);
        station = stationRepository.saveAndFlush(station);

        return stationMapper.toView(station);
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        if(!stationRepository.existsById(id))
            throw new EntityExistsException(String.format("Station with id [%s] not found", id));

        stationRepository.deleteById(id);

        return !stationRepository.existsById(id);
    }

    private Station findById(UUID id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Station with id [%s] not found", id)));
    }
}
