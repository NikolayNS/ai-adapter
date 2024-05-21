package com.dmitrenko.aiadapter.model.dto.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class StationView {
    private UUID id;
    private String externalId;
    private String name;
    private String location;
    private LocalDateTime createdAt;
}
