package com.dmitrenko.aiadapter.model.dto.openai.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ChatRequest {
    private String model;
    private List<ChatRequestTool> tools = new LinkedList<>();
    private List<ChatRequestMessage> messages = new LinkedList<>();
}
