package com.dmitrenko.aiadapter.service.domain.impl;

import com.dmitrenko.aiadapter.mapper.view.ProfileMapper;
import com.dmitrenko.aiadapter.model.dto.request.ProfileReq;
import com.dmitrenko.aiadapter.model.dto.view.ProfileDetailView;
import com.dmitrenko.aiadapter.model.dto.view.ProfileView;
import com.dmitrenko.aiadapter.model.entity.Profile;
import com.dmitrenko.aiadapter.model.entity.Station;
import com.dmitrenko.aiadapter.repository.AccountRepository;
import com.dmitrenko.aiadapter.repository.ProfileRepository;
import com.dmitrenko.aiadapter.service.domain.ProfileDomainService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileDomainServiceImpl implements ProfileDomainService {

    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    private final ProfileMapper profileMapper;

    @Override
    @Transactional
    public ProfileView add(ProfileReq req) {
        if(profileRepository.existsByFirstNameAndLastName(req.getFirstName(), req.getLastName()))
            throw new EntityExistsException(String.format("Profile with firstName [%s] and lastName [%s] already exist", req.getFirstName(), req.getLastName()));

        Profile profile = profileMapper.toEntity(req);
        profile = profileRepository.saveAndFlush(profile);

        return profileMapper.toView(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileView get(UUID id) {
        return profileMapper.toView(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileDetailView getDetail(UUID id) {
        return profileMapper.toDetailView(findById(id));
    }

    @Override
    @Transactional
    public ProfileDetailView addAccount(UUID id, UUID accountId) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with id [%s] not found", id)));

        Profile profile = findById(id);
        profile.setAccount(account);
        profile = profileRepository.saveAndFlush(profile);

        return profileMapper.toDetailView(profile);
    }

    @Override
    @Transactional
    public ProfileView update(UUID id, ProfileReq req) {
        Profile station = findById(id);
        station = profileMapper.merge(station, req);
        station = profileRepository.saveAndFlush(station);

        return profileMapper.toView(station);
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        if(!profileRepository.existsById(id))
            throw new EntityExistsException(String.format("Profile with id [%s] not found", id));

        profileRepository.deleteById(id);

        return !profileRepository.existsById(id);
    }

    private Profile findById(UUID id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Profile with id [%s] not found", id)));
    }
}
