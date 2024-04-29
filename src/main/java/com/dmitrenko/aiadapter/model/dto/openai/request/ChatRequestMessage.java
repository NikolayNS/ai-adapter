package com.dmitrenko.aiadapter.model.dto.openai.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatRequestMessage {
    private String role;
    private String content;
}
