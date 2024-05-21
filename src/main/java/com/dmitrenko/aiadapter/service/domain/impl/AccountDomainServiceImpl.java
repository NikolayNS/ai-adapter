package com.dmitrenko.aiadapter.service.domain.impl;

import com.dmitrenko.aiadapter.mapper.view.AccountMapper;
import com.dmitrenko.aiadapter.model.dto.request.AccountReq;
import com.dmitrenko.aiadapter.model.dto.view.AccountDetailView;
import com.dmitrenko.aiadapter.model.dto.view.AccountView;
import com.dmitrenko.aiadapter.model.entity.Account;
import com.dmitrenko.aiadapter.repository.AccountRepository;
import com.dmitrenko.aiadapter.service.domain.AccountDomainService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountDomainServiceImpl implements AccountDomainService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountView add(AccountReq req) {
        if(accountRepository.existsByEmail(req.getEmail()))
            throw new EntityExistsException(String.format("Account with email [%s] already exist", req.getEmail()));

        Account account = accountMapper.toEntity(req);
        account = accountRepository.saveAndFlush(account);

        return accountMapper.toView(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountView get(UUID id) {
        return accountMapper.toView(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDetailView getDetail(UUID id) {
        return accountMapper.toDetailView(findById(id));
    }

    @Override
    @Transactional
    public AccountView update(UUID id, AccountReq req) {
        Account account = findById(id);
        account = accountMapper.merge(account, req);
        account = accountRepository.saveAndFlush(account);

        return accountMapper.toView(account);
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        if(!accountRepository.existsById(id))
            throw new EntityExistsException(String.format("Account with id [%s] not found", id));

        accountRepository.deleteById(id);

        return !accountRepository.existsById(id);
    }

    private Account findById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with id [%s] not found", id)));
    }
}
