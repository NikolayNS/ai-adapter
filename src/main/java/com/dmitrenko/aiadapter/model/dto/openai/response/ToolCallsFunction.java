package com.dmitrenko.aiadapter.model.dto.openai.response;

import lombok.Data;

@Data
public class ToolCallsFunction {
    private String name;
    private String arguments;
}
