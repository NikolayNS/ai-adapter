package com.dmitrenko.aiadapter.model.dto.mqtt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DefinitionDTO {
	private String description;
	private Object exposes;
	private String model;
	private Object options;
	private String vendor;

	@JsonProperty("supports_ota")
	private Boolean supportsOta;
}
