package com.dmitrenko.aiadapter.mapper.view;

import com.dmitrenko.aiadapter.model.dto.request.ProfileReq;
import com.dmitrenko.aiadapter.model.dto.view.DeviceDetailView;
import com.dmitrenko.aiadapter.model.dto.view.ProfileDetailView;
import com.dmitrenko.aiadapter.model.dto.view.ProfileView;
import com.dmitrenko.aiadapter.model.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ProfileMapper {

    private final AccountMapper accountMapper;
    private final ConversationMapper conversationMapper;

    public ProfileMapper(@Lazy AccountMapper accountMapper, @Lazy ConversationMapper conversationMapper) {
        this.accountMapper = accountMapper;
        this.conversationMapper = conversationMapper;
    }

    public ProfileView toView(Profile source) {
        return new ProfileView()
                .setId(source.getId())
                .setFirstName(source.getFirstName())
                .setLastName(source.getLastName())
                .setCreatedAt(source.getCreatedAt());
    }

    public ProfileDetailView toDetailView(Profile source) {
        return new ProfileDetailView()
                .setId(source.getId())
                .setFirstName(source.getFirstName())
                .setLastName(source.getLastName())
                .setAccount(accountMapper.toView(source.getAccount()))
                .setConversations(conversationMapper.toView(source.getConversations()))
                .setCreatedAt(source.getCreatedAt());
    }

    public Profile toEntity(ProfileReq source) {
        return new Profile()
                .setFirstName(source.getFirstName())
                .setLastName(source.getLastName());
    }

    public Profile merge(Profile target, ProfileReq source) {
        return target
                .setFirstName(source.getFirstName() == null ? target.getFirstName() : source.getFirstName())
                .setLastName(source.getLastName() == null ? target.getLastName() : source.getLastName());
    }

    public List<ProfileView> toView(Collection<Profile> source) {
        return source.stream().map(this::toView).toList();
    }

    public List<ProfileDetailView> toDetailView(Collection<Profile> source) {
        return source.stream().map(this::toDetailView).toList();
    }

    public List<Profile> toEntity(Collection<ProfileReq> source) {
        return source.stream().map(this::toEntity).toList();
    }
}
