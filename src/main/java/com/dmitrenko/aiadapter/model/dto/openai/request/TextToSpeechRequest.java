package com.dmitrenko.aiadapter.model.dto.openai.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TextToSpeechRequest {
    private String model;
    private String input;
    private String voice;
}
