package com.dmitrenko.aiadapter.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StationReq {
    private String externalId;
    private String name;
    private String location;
}
