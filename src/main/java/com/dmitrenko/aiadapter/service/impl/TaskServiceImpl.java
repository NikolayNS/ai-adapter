package com.dmitrenko.aiadapter.service.impl;

import com.dmitrenko.aiadapter.model.dto.DeviceAction;
import com.dmitrenko.aiadapter.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final MQTTServiceImpl mqttService;

    @SneakyThrows
    @Override
    public void switchLamp(String action) {
        var objectMapper = new ObjectMapper();
        DeviceAction deviceAction = objectMapper.readValue(action, DeviceAction.class);
	    log.info("Action: {}, number: {}", deviceAction.getAction(), deviceAction.getNumber());

		// Just for test. Only lamp with number 3 is connected to the MQTT server
	    // TODO: needs to be reworked after the requirements are clear
		if (deviceAction.getNumber() == 3) {
			// TODO: names must be taken from the database or some other source, not hardcoded
			mqttService.sendSwitchCommand("TestSocket", deviceAction.getAction().toUpperCase());
		}
    }

    @SneakyThrows
    @Override
    public void switchSocket(String action) {
        var objectMapper = new ObjectMapper();
        DeviceAction deviceAction = objectMapper.readValue(action, DeviceAction.class);
	    log.info("Action: {}, number: {}", deviceAction.getAction(), deviceAction.getNumber());
    }

    @SneakyThrows
    @Override
    public void setACTemperature(String action) {
        var objectMapper = new ObjectMapper();
        DeviceAction deviceAction = objectMapper.readValue(action, DeviceAction.class);
        log.info("Action: {}, number: {}", deviceAction.getAction(), deviceAction.getNumber());
    }

	@Override
	public void testAction() {
		log.info("Test action");
		mqttService.getDevices();
	}
}
