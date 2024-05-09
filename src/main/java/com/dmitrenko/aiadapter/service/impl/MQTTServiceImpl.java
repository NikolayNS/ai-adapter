package com.dmitrenko.aiadapter.service.impl;

import java.util.List;

import com.dmitrenko.aiadapter.model.dto.mqtt.CommandMessage;
import com.dmitrenko.aiadapter.service.MQTTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class MQTTServiceImpl implements MQTTService {

	private final MqttClient mqttClient;

	@Override
	public void sendSwitchCommand(String deviceName, String action) {
		String topic = "zigbee2mqtt/" + deviceName + "/set";
		String messageContent = createJsonMessage(action);
		sendMessage(topic, messageContent);
	}

	@Override
	public List<String> getDevices() {
		try {
			String topic = "zigbee2mqtt/bridge/devices";
			mqttClient.subscribe(topic, (topic1, msg) -> {
				byte[] payload = msg.getPayload();
				// Handle message, assuming it's a JSON string with device information
//				log.info("Received message: {}", new String(payload));
				System.out.println();
			});

			mqttClient.publish(topic, new MqttMessage());
		} catch (MqttException e) {
			log.error("Error while sending message", e);
		}
		return null;
	}

	public void sendMessage(String topic, String content) {
		try {
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(2);
			mqttClient.publish(topic, message);
		} catch (MqttException e) {
			log.error("Error while sending message", e);
		}
	}

	private static String createJsonMessage(String action) {
		try {
			var objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(new CommandMessage(action));
		} catch (JsonProcessingException e) {
			log.error("Error while creating json message", e);
			return "{}";
		}
	}
}
