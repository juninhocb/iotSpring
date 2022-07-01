package com.iot_server.iot_server.resources.testResources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iot_server.iot_server.config.MqttGatewayConfig;

@RestController
@Profile("test")
public class MqttResource {

    @Autowired
    MqttGatewayConfig mqttGateway;
    
    @PostMapping("/sendMessage")
    public ResponseEntity<?> publish(@RequestBody String mqttMessage){

        try{
            JsonObject convertObject = new Gson().fromJson(mqttMessage, JsonObject.class);
            mqttGateway.sendToMqtt(convertObject.get("message").toString(), convertObject.get("topic").toString());
            return ResponseEntity.ok("Sucess");

        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.ok("Fail -> Verifique se o servidor está em profile de desenvolvimento");
        }

    }
}
