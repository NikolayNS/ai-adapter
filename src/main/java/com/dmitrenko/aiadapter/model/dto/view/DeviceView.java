package com.dmitrenko.aiadapter.model.dto.view;

import com.dmitrenko.aiadapter.model.entity.DeviceStatus;
import com.dmitrenko.aiadapter.model.entity.action.Action;
import com.dmitrenko.aiadapter.model.entity.action.DeviceAction;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Accessors(chain = true)
public class DeviceView {
    private UUID id;
    private String externalId;
    private String name;
    private String description;
    private Set<DeviceAction> actions = new HashSet<>();
    private DeviceStatus status;
    private LocalDateTime createdAt;
}
