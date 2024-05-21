package com.dmitrenko.aiadapter.model.entity.action;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;
import java.util.stream.Collectors;

@Converter
public class DeviceActionEnumConverter implements AttributeConverter<Set<Action>, String[]> {

    @Override
    public String[] convertToDatabaseColumn(Set<Action> actions) {
        if (actions == null || actions.isEmpty()) return new String[0];

        return actions.stream().map(Action::getValue).toArray(String[]::new);
    }

    @Override
    public Set<Action> convertToEntityAttribute(String[] strings) {
        if (strings == null || strings.length == 0) return new HashSet<>();

        return Arrays.stream(strings).map(DeviceAction::fromValue).collect(Collectors.toSet());
    }
}
