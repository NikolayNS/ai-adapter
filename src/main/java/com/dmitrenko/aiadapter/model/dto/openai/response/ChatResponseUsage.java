package com.dmitrenko.aiadapter.model.dto.openai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatResponseUsage {
    @JsonProperty("completion_tokens")
    private String completionTokens;
    @JsonProperty("prompt_tokens")
    private String promptTokens;
    @JsonProperty("total_tokens")
    private String totalTokens;
}
