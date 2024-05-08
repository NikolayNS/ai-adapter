package com.dmitrenko.aiadapter.service.impl;

import com.dmitrenko.aiadapter.model.dto.DeviceAction;
import com.dmitrenko.aiadapter.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final MQTTService mqttService;

    @SneakyThrows
    @Override
    public void switchLamp(String action) {
        var objectMapper = new ObjectMapper();
        DeviceAction deviceAction = objectMapper.readValue(action, DeviceAction.class);
        System.out.println("switchLamp");
		// Just for test. Only lamp with number 3 is connected to the MQTT server
	    // TODO: needs to be reworked after the requirements are clear
		if (deviceAction.getNumber() == 3) {
			// TODO: names must be taken from the database or some other source, not hardcoded
			mqttService.sendSwitchCommand("TestSocket", deviceAction.getAction().toUpperCase());
		}
        System.out.printf("Action: %s, number: %s%n", deviceAction.getAction(), deviceAction.getNumber());
    }

    @SneakyThrows
    @Override
    public void switchSocket(String action) {
        var objectMapper = new ObjectMapper();
        DeviceAction deviceAction = objectMapper.readValue(action, DeviceAction.class);
        System.out.println("switchSocket");
        System.out.printf("Action: %s, number: %s%n", deviceAction.getAction(), deviceAction.getNumber());
    }

    @SneakyThrows
    @Override
    public void setACTemperature(String action) {
        var objectMapper = new ObjectMapper();
        DeviceAction deviceAction = objectMapper.readValue(action, DeviceAction.class);
        System.out.println("setACTemperature");
        System.out.printf("Action: %s, number: %s%n", deviceAction.getAction(), deviceAction.getNumber());
    }
}
