package com.iot_server.iot_server.entities;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

public class MqttProperties {

    private final String host = "11ecd64be4a14dd0b10818f3c750d86b.s2.eu.hivemq.cloud";
    private final String username = "juninhocb";
    private final String password = "palmeiras";

    final Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();

    public MqttProperties() {
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Mqtt5BlockingClient getClient() {
        return client;
    }


    

    
}
