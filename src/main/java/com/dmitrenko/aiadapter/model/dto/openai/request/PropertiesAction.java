package com.dmitrenko.aiadapter.model.dto.openai.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PropertiesAction<T> {
    private String type;
    private T[] action;
}
