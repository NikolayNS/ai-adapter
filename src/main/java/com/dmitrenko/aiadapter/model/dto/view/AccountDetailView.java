package com.dmitrenko.aiadapter.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class AccountDetailView {
    private UUID id;
    private String email;
    private List<StationView> stations = new LinkedList<>();
    private List<ProfileView> profiles = new LinkedList<>();
    private LocalDateTime createdAt;
}
