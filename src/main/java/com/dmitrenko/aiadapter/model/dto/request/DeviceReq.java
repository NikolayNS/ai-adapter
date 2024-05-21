package com.dmitrenko.aiadapter.model.dto.request;

import com.dmitrenko.aiadapter.model.entity.action.DeviceAction;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class DeviceReq {
    private String externalId;
    private String name;
    private String description;
    private Set<DeviceAction> actions;
}
