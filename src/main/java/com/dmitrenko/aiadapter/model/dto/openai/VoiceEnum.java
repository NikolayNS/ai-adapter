package com.dmitrenko.aiadapter.model.dto.openai;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VoiceEnum {
    ALLOY("alloy"),
    ECHO("echo"),
    FABLE("fable"),
    ONYX("onyx"),
    NOVA("nova"),
    SHIMMER("shimmer");

    private final String value;

    VoiceEnum(String value) {
        this.value = value;
    }

    public static VoiceEnum fromValue(String value) {
        return Arrays.stream(VoiceEnum.values())
                .filter(o -> o.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new EnumValueNotFoundException(String.format("Unexpected VoiceEnum value %s", value)));
    }
}
