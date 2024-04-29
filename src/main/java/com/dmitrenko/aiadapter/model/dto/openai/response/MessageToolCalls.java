package com.dmitrenko.aiadapter.model.dto.openai.response;

import lombok.Data;

@Data
public class MessageToolCalls {
    private String id;
    private String type;
    private ToolCallsFunction function;
}
