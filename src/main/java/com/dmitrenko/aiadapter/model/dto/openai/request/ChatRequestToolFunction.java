package com.dmitrenko.aiadapter.model.dto.openai.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatRequestToolFunction {
    private String description;
    private String name;
    private FunctionParameters parameters;
}
