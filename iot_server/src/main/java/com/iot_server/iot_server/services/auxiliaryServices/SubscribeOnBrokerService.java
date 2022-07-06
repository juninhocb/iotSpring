package com.iot_server.iot_server.services.auxiliaryServices;


import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot_server.iot_server.entities.Device;
import com.iot_server.iot_server.entities.MqttProperties;
import com.iot_server.iot_server.repositories.DeviceRepository;


@Service
public class SubscribeOnBrokerService {
    Boolean isResponse = false;

    @Autowired
    DeviceRepository dRepository; 

    public Boolean subAndWait(Device deviceHandle) { 
        MqttProperties master =  new MqttProperties();
        connAndSub(master, deviceHandle);
        return isResponse; 
    }

    public void connAndSub(MqttProperties master, Device deviceHandle) {
        
        isResponse = false; 
        System.out.println("[SUBSCRIBE] AGUARDANDO RESPOSTA DISPOSITIVO: " + deviceHandle.getName() + "................"); 
        
          
        try {
            master.getClient().connectWith()
                .simpleAuth()
                .username(master.getUsername())
                .password(UTF_8.encode(master.getPassword()))
                .applySimpleAuth()
                .send();
        
            master.getClient().subscribeWith()
                .topicFilter(deviceHandle.getName()+"Alive")
                .send();

            master.getClient().toAsync().publishes(ALL, publish -> 
            {
                System.out.println("[ASYNC SUBSCRIBE] DISPOSITIVO RESPONDEU: " + deviceHandle.getName());
                isResponse = true; 
                //deviceHandle.setIsConnected(true);
                //dRepository.save(deviceHandle);
                master.getClient().disconnect();  
                
            });
            
            try{
                Thread.sleep(5000);
            } catch(InterruptedException ex){
                ex.printStackTrace();
            }
            
            
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            System.out.println("[SUBSCRIBE] EXCEPTION");
        }
        

    }

}
