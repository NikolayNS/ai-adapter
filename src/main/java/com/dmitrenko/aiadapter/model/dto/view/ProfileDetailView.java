package com.dmitrenko.aiadapter.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class ProfileDetailView {
    private UUID id;
    private String firstName;
    private String lastName;
    private AccountView account;
    private List<ConversationView> conversations = new LinkedList<>();
    private LocalDateTime createdAt;
}
