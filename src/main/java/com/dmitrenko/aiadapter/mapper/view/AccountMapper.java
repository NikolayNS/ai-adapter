package com.dmitrenko.aiadapter.mapper.view;

import com.dmitrenko.aiadapter.model.dto.request.AccountReq;
import com.dmitrenko.aiadapter.model.dto.view.AccountDetailView;
import com.dmitrenko.aiadapter.model.dto.view.AccountView;
import com.dmitrenko.aiadapter.model.entity.Account;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AccountMapper {

    private final StationMapper stationMapper;
    private final ProfileMapper profileMapper;

    public AccountMapper(@Lazy StationMapper stationMapper, @Lazy ProfileMapper profileMapper) {
        this.stationMapper = stationMapper;
        this.profileMapper = profileMapper;
    }

    public AccountView toView(Account source) {
        return new AccountView()
                .setId(source.getId())
                .setEmail(source.getEmail())
                .setCreatedAt(source.getCreatedAt());
    }

    public AccountDetailView toDetailView(Account source) {
        return new AccountDetailView()
                .setId(source.getId())
                .setEmail(source.getEmail())
                .setStations(stationMapper.toView(source.getStations()))
                .setProfiles(profileMapper.toView(source.getProfiles()))
                .setCreatedAt(source.getCreatedAt());
    }

    public Account toEntity(AccountReq source) {
        return new Account()
                .setEmail(source.getEmail());
    }

    public Account merge(Account target, AccountReq source) {
        return target
                .setEmail(source.getEmail() == null ? target.getEmail() : source.getEmail());
    }

    public List<AccountView> toView(Collection<Account> source) {
        return source.stream().map(this::toView).toList();
    }

    public List<AccountDetailView> toDetailView(Collection<Account> source) {
        return source.stream().map(this::toDetailView).toList();
    }

    public List<Account> toEntity(Collection<AccountReq> source) {
        return source.stream().map(this::toEntity).toList();
    }
}
