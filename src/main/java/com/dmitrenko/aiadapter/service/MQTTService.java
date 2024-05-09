package com.dmitrenko.aiadapter.service;

import java.util.List;

public interface MQTTService {
	List<String> getDevices();
	void sendSwitchCommand(String deviceName, String action);
}
