package com.dmitrenko.aiadapter.model.dto.openai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ChatResponse {
    private String id;
    private List<ChatResponseChoice> choices = new LinkedList<>();
    private Long created;
    private String model;
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;
    private String object;
    private ChatResponseUsage usage;
}
