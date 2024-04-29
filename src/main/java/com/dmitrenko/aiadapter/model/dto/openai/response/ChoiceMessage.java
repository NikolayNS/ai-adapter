package com.dmitrenko.aiadapter.model.dto.openai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ChoiceMessage {
    private String role;
    private String content;
    @JsonProperty("tool_calls")
    private List<MessageToolCalls> toolCalls = new LinkedList<>();
}
