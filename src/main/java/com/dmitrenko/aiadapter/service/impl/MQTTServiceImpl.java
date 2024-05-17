package com.dmitrenko.aiadapter.service.impl;

import java.util.List;

import com.dmitrenko.aiadapter.model.dto.mqtt.CommandMessage;
import com.dmitrenko.aiadapter.model.dto.mqtt.InterviewEventDTO;
import com.dmitrenko.aiadapter.model.dto.mqtt.RawEventDTO;
import com.dmitrenko.aiadapter.service.MQTTService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
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

	@PostConstruct
	public void init() {
		try {
			mqttClient.subscribe("zigbee2mqtt/bridge/event", (topic, msg) -> {
				log.trace("Topic fired: " + topic);
				var objectMapper = new ObjectMapper();
				String payload = new String(msg.getPayload());
				RawEventDTO rawEvent = objectMapper.readValue(payload, RawEventDTO.class);
				log.info("Received message of type: " + rawEvent.getType());

				switch (rawEvent.getType()) {
					case DEVICE_JOINED:
						log.info("Device joined event");
						break;
					case DEVICE_INTERVIEW:
						log.info("A new device has been added!");
						InterviewEventDTO event = objectMapper.readValue(payload, InterviewEventDTO.class);
						log.info("Device details: {}", event);
						// TODO: here we need to add device to DB

						// TODO: after device saved - need to make GPT ask about name and intention to use and update device in DB
						break;
					case DEVICE_ANNOUNCE:
						log.info("Device announce event");
						break;
					case DEVICE_LEAVE:
						// TODO: delete device from db or mark as deleted
						log.info("Device leave event");
						break;
					default:
						log.warn("Unknown event type: " + rawEvent.getType());
				}
			});
		} catch (MqttException e) {
			log.error("Error while connecting to MQTT broker", e);
		}
	}

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
				log.trace("Topic fired: " + topic);
				byte[] payload = msg.getPayload();
				// Handle message, assuming it's a JSON string with device information
				log.info("Received message: {}", new String(payload));
				System.out.println();
			});

			mqttClient.publish(topic, new MqttMessage());
		} catch (MqttException e) {
			log.error("Error while sending message", e);
		}
		return null;
	}

	@Override
	public void getDevice(String ieeeAddress) {
		// TODO: need? currently unused. Not sure it works as we need
		try {
			String topic = "zigbee2mqtt/" + ieeeAddress;
			mqttClient.subscribe(topic, (topic1, msg) -> {
				log.trace("Topic fired: " + topic);
				byte[] payload = msg.getPayload();
				// Handle message, assuming it's a JSON string with device information
				log.info("Received message: {}", new String(payload));
				System.out.println();
			});

			mqttClient.publish(topic + "/get", new MqttMessage());
		} catch (MqttException e) {
			log.error("Error while sending message", e);
		}
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
