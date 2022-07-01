package com.iot_server.iot_server.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutBoundChannel")
public interface MqttGatewayConfig {
    
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}
