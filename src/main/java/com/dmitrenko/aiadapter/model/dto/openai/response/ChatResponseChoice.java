package com.dmitrenko.aiadapter.model.dto.openai.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChatResponseChoice {
    private Long index;
    private ChoiceMessage message;
}
