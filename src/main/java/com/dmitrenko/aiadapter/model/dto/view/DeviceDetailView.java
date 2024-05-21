package com.dmitrenko.aiadapter.model.dto.view;

import com.dmitrenko.aiadapter.model.entity.DeviceStatus;
import com.dmitrenko.aiadapter.model.entity.action.Action;
import com.dmitrenko.aiadapter.model.entity.action.DeviceAction;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class DeviceDetailView {
    private UUID id;
    private String externalId;
    private String name;
    private String description;
    private Set<DeviceAction> actions = new HashSet<>();
    private DeviceStatus status;
    private StationView station;
    private LocalDateTime createdAt;
}
