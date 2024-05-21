package com.dmitrenko.aiadapter.service.domain;

import com.dmitrenko.aiadapter.model.dto.request.ProfileReq;
import com.dmitrenko.aiadapter.model.dto.view.ProfileDetailView;
import com.dmitrenko.aiadapter.model.dto.view.ProfileView;

import java.util.UUID;

public interface ProfileDomainService {

    ProfileView add(ProfileReq req);

    ProfileView get(UUID id);

    ProfileDetailView getDetail(UUID id);

    ProfileDetailView addAccount(UUID id, UUID accountId);

    ProfileView update(UUID id, ProfileReq req);

    boolean delete(UUID id);
}
