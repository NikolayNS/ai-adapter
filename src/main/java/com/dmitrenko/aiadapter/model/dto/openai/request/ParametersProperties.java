package com.dmitrenko.aiadapter.model.dto.openai.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ParametersProperties {
    private PropertiesAction<?> action;
    private PropertiesNumber number;
}
