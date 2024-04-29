package com.dmitrenko.aiadapter.model.dto.openai;

import com.dmitrenko.aiadapter.exception.EnumValueNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MessageRoleEnum {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String value;

    MessageRoleEnum(String value) {
        this.value = value;
    }

    public static MessageRoleEnum fromValue(String value) {
        return Arrays.stream(MessageRoleEnum.values())
                .filter(o -> o.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new EnumValueNotFoundException(String.format("Unexpected MessageRole value %s", value)));
    }
}