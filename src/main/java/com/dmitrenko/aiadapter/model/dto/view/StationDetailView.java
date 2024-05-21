package com.dmitrenko.aiadapter.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class StationDetailView {
    private UUID id;
    private String externalId;
    private String name;
    private String location;
    private AccountView account;
    private List<DeviceView> devices = new LinkedList<>();
    private LocalDateTime createdAt;
}
