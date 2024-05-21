package com.dmitrenko.aiadapter.service.domain;

import com.dmitrenko.aiadapter.model.dto.request.AccountReq;
import com.dmitrenko.aiadapter.model.dto.view.AccountDetailView;
import com.dmitrenko.aiadapter.model.dto.view.AccountView;

import java.util.UUID;

public interface AccountDomainService {

    AccountView add(AccountReq req);

    AccountView get(UUID id);

    AccountDetailView getDetail(UUID id);

    AccountView update(UUID id, AccountReq req);

    boolean delete(UUID id);
}
