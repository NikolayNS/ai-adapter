package com.dmitrenko.aiadapter.config;

import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class MqttConfig {
	@Value("${integration.mqtt.url}")
	private String url;
    private static final String CLIENT_ID = "clientId";

    @Bean
    public MqttClient mqttClient() {
        try {
            MqttClient client = new MqttClient(url, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
			connOpts.setAutomaticReconnect(true);
            client.connect(connOpts);
            return client;
        } catch (Exception e) {
			log.error("Error creating MQTT client", e);
            throw new RuntimeException("Error creating MQTT client", e);
        }
    }
}