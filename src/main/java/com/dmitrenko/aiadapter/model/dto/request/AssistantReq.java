package com.dmitrenko.aiadapter.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AssistantReq {
    private String externalId;
}
