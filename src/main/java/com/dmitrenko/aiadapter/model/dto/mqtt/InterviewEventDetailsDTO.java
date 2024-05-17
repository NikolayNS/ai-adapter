package com.dmitrenko.aiadapter.model.dto.mqtt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InterviewEventDetailsDTO {
    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("ieee_address")
    private String ieeeAddress;

	private DefinitionDTO definition;

	private String status;

	private Boolean supported;
}