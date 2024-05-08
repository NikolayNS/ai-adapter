package com.dmitrenko.aiadapter.service.impl;

import com.dmitrenko.aiadapter.model.dto.mqtt.CommandMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MQTTService {

	@Value("${integration.mqtt.url}")
	private String url;

	private static final String CLIENT_ID = MqttClient.generateClientId();
	private static final MemoryPersistence PERSISTENCE = new MemoryPersistence();

	public void sendSwitchCommand(String deviceName, String action) {
		String topic = "zigbee2mqtt/" + deviceName + "/set";
		String messageContent = createJsonMessage(action);
		sendMessage(topic, messageContent);
	}

	public void sendMessage(String topic, String content) {
		try (MqttClient client = new MqttClient(url, CLIENT_ID, PERSISTENCE)) {
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			client.connect(connOpts);

			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(2);
			client.publish(topic, message);

			client.disconnect();
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
